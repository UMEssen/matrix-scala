from logging import PlaceHolder
import swagger as swagger
from codegen.data import ContainerModel, DataModel, GenericType, Operation, Argument, PolymorphicModel, PolymorphicType, PrimitiveType, ModelType, ResponseBoxModel, ResponseType, ResponsesTraitModel, StatusCodeRange, UnitType, is_reserved
from transformations.data import MutableContext
from transformations.security import transform_security_type
from transformations.util import attach_model, flatten_2d, PLACEHOLDER, wrap_in_option
from transformations.types import transform_schema
from transformations.util import to_model_name
from transformations.primitive import primitive_mapping

from typing import Container, cast

from typing import Dict, List, Optional, TypeVar
from pprint import pp, pprint
from dataclasses import dataclass
from dataclasses import dataclass
import http.client

@dataclass
class PathTuple:
    endpoint: str
    http_method: str
    operation: swagger.Operation

def get_defined_operations(item: swagger.PathItem, endpoint: str) -> List[PathTuple]:
    # HACK
    return [
        PathTuple(endpoint, http_method, operation)
        for (http_method, operation)
        in filter(lambda t: t[1] is not None, item.__dict__.items())
    ]



# just so there is no extra transformation logic
def parameter_as_schema(p: swagger.Parameter) -> swagger.Schema:
    return swagger.Schema(
        title=None, description=p.description, type=p.type,format=p.format,items=p.items,allOf=None,additionalProperties=False,default=None,maximum=None,exclusiveMaximum=None,minimum=None,exclusiveMinimum=None,maxLength=None,minLength=None,pattern=None,maxItems=None,minItems=None,uniqueItems=None,properties=None,required=None,enum=None,multipleOf=None,
    )
@dataclass
class ParameterTuple:
    location: swagger.ParameterLocation
    arg: Argument



# TODO: is a destinciton between simple and body needed anymore?
def transform_simple_argument(ctx: MutableContext, sp: swagger.Parameter, parent: PolymorphicModel) -> ParameterTuple:
    assert sp.in_ != swagger.ParameterLocation.Body    

    required = sp.required or False # 
    type = wrap_in_option(required,  transform_schema(ctx, parameter_as_schema(sp), sp.name, parent))
    arg = Argument(
        description=sp.description,
        name=sp.name,
        escape=is_reserved(sp.name),
        required=sp.required if sp.required is not None else False,
        type=type,

    )
    return ParameterTuple(
        location=sp.in_,
        arg=arg,
    )

@dataclass
class BodyParameterTuple:
    model: Optional[PolymorphicModel] # None when it is a simple type like text
    type: PolymorphicType


def suggested_response_name(status_code: StatusCodeRange) -> str:
    status_string = http.client.responses.get(status_code.start)
    assert status_string, f"invalid http code {status_code}"
    return to_model_name(status_string)

@dataclass
class ResponsesTuple:
    responses_type: PolymorphicType
    responses_type_children: List[ResponseType]


T = TypeVar('T')
def inline_assert(a: T | None) -> T:
    assert a is not None
    return a

def transform_body(ctx: MutableContext, body_parameter: swagger.Parameter, container: PolymorphicModel) -> PolymorphicType:
    assert body_parameter.in_ == swagger.ParameterLocation.Body
    assert body_parameter.schema
    suggested_name = to_model_name("Body")
    # TODO: overwrite description in model
    body_type = transform_schema(ctx, body_parameter.schema, suggested_name,container)
    if body_parameter.description:
        inject_description(body_type, body_parameter.description)
    
    return body_type

def inject_description(t: PolymorphicType, description: str):
    # add description if not given
    #if tt.determined_type.is_model and tt.determined_type.data.is_data_model:
    #    body_model: PolymorphicModel = tt.determined_type.data.model
    #    body_model.description = body_model.description or description 
    pass

def wrap_in_response_box(t: PolymorphicType, responses_trait_model: PolymorphicModel, suggested_name: str) -> PolymorphicModel:
    response_box = ResponseBoxModel(model_name=f"{suggested_name}Box",boxes = t, of_response_trait=responses_trait_model).to_polymorphic()
    return response_box


def bound_builder(str_code: str, wildcard_value: int) -> int:
    return sum([
        ((10**(2-i)*int(str_code[i])) # value at a given position
        if str_code[i].isdigit() # check if it is not a wildcard
        else wildcard_value*(10**(2-i))) # replace the wild_card
        for i in range(3) # go through all three digits
    ]) 
# some parts of the specification use wildcards as 3xx 
# TODO: wildcards should only be ranges, not something like 4x5
def parse_status_code_range(str_code: str) -> StatusCodeRange:
    assert len(str_code) == 3, f"invalid http code wildcard given: {str_code}"
    status_code_range = StatusCodeRange(
        start=bound_builder(str_code,0), end=bound_builder(str_code,9)
    )
    return status_code_range

def attach_transformation_response_trait(response_type: ResponseType, responses_trait_model: PolymorphicModel,container: PolymorphicModel) -> ResponseType:
    ptype, code_range = response_type.type, response_type.status_code_range
    is_direct_child = ptype.is_model and  cast(ModelType, ptype.data).model.is_data_model and cast(ModelType, ptype.data).model.data.parent == container
    if not is_direct_child:
        suggested_name = suggested_response_name(code_range)
        box = wrap_in_response_box(ptype, responses_trait_model,suggested_name)
        attach_model(box, container)
        return ResponseType(code_range, ModelType(box).to_polymorphic())
    else:
        data_model:DataModel = cast(DataModel,cast(ModelType, ptype.data).model.data)
        data_model.of_response_trait = responses_trait_model
        return ResponseType(code_range, ptype)


#Codes used in the spec: {429, 400, 401, 403, 404, 405, 502, '3xx', 504, 409, '4xx', 501, 413}

# the specification specifies some return codes, but responses are not bound to these
# we preferably want to let the generated code do errorhandling
# otherwise there is a ugly duality between defined errors and non-defined ones
def is_error_code(code: int) -> bool:
    return not(200 <= code and 299 >= code) and not(300 <= code and 399 >= code) and not(code == 418) # :-)

def transform_to_responses_container(ctx: MutableContext, responses: swagger.Responses, container: PolymorphicModel) -> ResponsesTuple:
    codes = list(responses.keys())
    # TODO: headers and description are silently discarded here!

    responses_trait_model = ResponsesTraitModel(model_name="Responses").to_polymorphic()
    schema_code_range_tuples = [
        (response.schema, parse_status_code_range(str(code))) # casted to str since a code can either be a int or a wildcard str
        for (code, response) in responses.items()
        if isinstance(code, int) and not(is_error_code(code))

    ]
    response_types_plain = [
        ResponseType(status_code_range=status_code_range, type =
                     # either transform the given schema, or just backup with empty json Object 
                     transform_schema(ctx, schema, suggested_response_name(status_code_range), container) if schema is not None else GenericType().to_polymorphic()
        )
        for (schema, status_code_range) in schema_code_range_tuples
    ]

    assert len(response_types_plain) >= 1, "no responses :("

    # no need for pattern matching, if there is only one type
    if len(response_types_plain) == 1:
        head = response_types_plain[0]
        return ResponsesTuple(
            responses_type = head.type,
            responses_type_children=[head],
        )

    else:
        attached_types = [attach_transformation_response_trait(type, responses_trait_model, container) for type in response_types_plain ]
        responses_type = ModelType(responses_trait_model).to_polymorphic()

        attach_model(responses_trait_model,container)
        return ResponsesTuple(
            responses_type=responses_type, 
            responses_type_children=attached_types,
        )

@dataclass
class ContainerTuple:
    body_type: PolymorphicType
    has_body: bool
    model: PolymorphicModel
    response_type: PolymorphicType
    response_types: List[ResponseType]

# a namespace for the body and the responses
def transform_to_operation_container(ctx: MutableContext, sop: swagger.Operation) -> ContainerTuple:
    assert sop.operationId
    container_name=to_model_name(sop.operationId)
    container_model = ContainerModel(
        parent=None,
        model_name=container_name,
    ).to_polymorphic()
    responses_tuple = transform_to_responses_container(ctx, sop.responses, container_model)    

    body_parameters = [p for p in sop.parameters or [] if p.in_ == swagger.ParameterLocation.Body] 
    assert len(body_parameters) <= 1, "multiple bodies defined"
    # matrix requires empty requests to send an empty json body
    body_type = transform_body(ctx, body_parameters[0], container_model)if len(body_parameters) > 0 else GenericType().to_polymorphic()

    return ContainerTuple(
        model=container_model,
        body_type=body_type,
        has_body=len(body_parameters) > 0,
        response_type=responses_tuple.responses_type,
        response_types=responses_tuple.responses_type_children,
        #default_response_type=responses_tuple.default_type,
    )

def transform_to_operation(ctx: MutableContext, path: PathTuple) -> Operation:
    assert path.operation.operationId
    container_tuple = transform_to_operation_container(ctx, path.operation)
    #assert path.operation.parameters
    parameters = path.operation.parameters or []
    simple_params = filter(lambda p: p.in_ !=swagger.ParameterLocation.Body, parameters)
    simple_args: List[ParameterTuple] = [transform_simple_argument(ctx, p, container_tuple.model) for p in simple_params]
    query_args = [a.arg for a in simple_args if a.location == swagger.ParameterLocation.Query]
    header_args = [a.arg for a in simple_args if a.location == swagger.ParameterLocation.Header]
    path_args = [a.arg for a in simple_args if a.location == swagger.ParameterLocation.Path]
    
    security = transform_security_type(path.operation)
    op = Operation(
        operation_id=path.operation.operationId,
        deprecated=path.operation.deprecated if path.operation.deprecated is not None else False, #could also be None
        summary=path.operation.summary,
        description=path.operation.description,
        endpoint=path.endpoint,
        http_method=path.http_method.upper(), # TODO: stricter types
        response_type=container_tuple.response_type,
        path_args=path_args,
        query_args=query_args,
        header_args=header_args,
        has_body=container_tuple.has_body,
        body_type=container_tuple.body_type,
        container_model=container_tuple.model,
        response_types=container_tuple.response_types,
        security=security,
    )
    return op

# NOTE: foo
def transform_to_operations(ctx: MutableContext, swagger_data: swagger.Swagger) -> list[Operation]:
    assert swagger_data.basePath
    path_tuples = flatten_2d([
        get_defined_operations(path_item,swagger_data.basePath+relative_path)
        for relative_path, path_item in swagger_data.paths.items()
    ])
    operations = [
        transform_to_operation(ctx, tuple)
        for tuple in path_tuples
    ]
    return operations
