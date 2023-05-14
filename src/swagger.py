# NOTE:
# this swagger parser does NOT try to be a full implementation
# it ONLY implements features used by the matrix-spec, so a few shortcuts are made
# it also mostly implements spec-features which are relevant to code execution
# checkout out the swagger spec: https://swagger.io/specification/v2
from __future__ import annotations
from enum import Enum
from strenum import StrEnum
import os
from typing import Any, Dict, List, Mapping, Optional, Set, Type, TypeVar, Union
from glob import glob
from dataclasses import dataclass, field
from mashumaro.mixins.yaml import DataClassYAMLMixin, Decoder, EncodedData, default_decoder
from mashumaro import DataClassDictMixin, field_options
import pathlib


@dataclass
class ParseContext:
    refs: Set[str]
    spec_base_path: Optional[str]
    swagger_path: Optional[str] = None

# TODO: this is plain and utter garbage
def resolve_path(base_path: str, path: str) -> str:
    path_abs = os.path.abspath(path)
    base_abs = os.path.abspath(base_path)
    common_prefix = os.path.relpath(path_abs, base_abs)
    resolved = os.path.join(base_path, common_prefix)
    return str(resolved)


# global and ugly, but I got tired of hacking around with mashumaro
global_parse_context: ParseContext = ParseContext(
    swagger_path=None,
    spec_base_path=None,
    refs=set()
)

def get_parse_context() -> ParseContext:
    global global_parse_context
    return global_parse_context


@dataclass
class Reference(DataClassDictMixin):
    ref: str= field(metadata=field_options(alias="$ref"))

    @classmethod
    def __post_deserialize__(cls: Type[Reference], obj: Reference) -> Reference:
        ctx = get_parse_context()
        assert ctx.swagger_path, "failed to set swagger path"
        assert ctx.spec_base_path, "failed to set spec base_path"
        swagger_base_path = os.path.dirname(ctx.swagger_path)
        abs = resolve_path(ctx.spec_base_path,os.path.join(swagger_base_path, obj.ref))
        obj.ref = abs
        ctx.refs.add(abs)
        return obj



class SwaggerDataType(Enum):
    String = "string"
    Number = "number"
    Integer = "integer"
    Boolean = "boolean"
    Array = "array"
    Object = "object"
    File = "file"
    Null = "null"

@dataclass
class Schema(DataClassYAMLMixin):
    # http://json-schema.org/
    title: Optional[str] = None
    description: Optional[str] = None
    type: Optional[Union[list[SwaggerDataType],SwaggerDataType]] = None
    format: Optional[FormatEnum] = None
    items: Optional[Union[Union[Reference, Schema],List[Union[Reference, Schema]]]] = None

    allOf: Optional[list[Union[Reference, Schema]]] = None  # never references

    additionalProperties: Optional[Union[Union[Reference, Schema], bool]] = None
    default: Optional[Any] = None
    maximum: Optional[float] = None
    exclusiveMaximum: Optional[bool] = None
    minimum: Optional[float] = None
    exclusiveMinimum: Optional[bool] = None
    maxLength: Optional[int] = None
    minLength: Optional[int] = None
    pattern: Optional[str] = None
    maxItems: Optional[int] = None
    minItems: Optional[int] = None
    uniqueItems: Optional[bool] = None
    properties: Optional[dict[str, Union[Reference, Schema]]] = None
    required: Optional[list[str]] = None

    enum: Optional[list[Any]] = None
    multipleOf: Optional[float] = None

    defined_in_path: str = "PLACEHOLDER" # will be overriden in the post_deserialize hook

    @classmethod
    def __post_deserialize__(cls: Type[Schema], obj: Schema) -> Schema:
        ctx = get_parse_context()
        assert ctx.swagger_path, "failed to set swagger path"
        obj.defined_in_path = ctx.swagger_path
        return obj

class FormatEnum(StrEnum):
    Int32 = "int32"
    Int64 = "int64"
    Float = "float"
    Double = "double"
    Password = "password"
    Uri = "uri"
    Byte = "byte"




Example = Dict[str, Any]  # mime-type to example, incomplete for now!


@dataclass
class Header(DataClassDictMixin):
    type: SwaggerDataType  # "string", "number", "integer", "boolean", or "array"
    description: Optional[str] = None
    format: Optional[str] = None
    collectionFormat: Optional[str] = None  # "string", "number", "integer", "boolean", or "array"
    default: Optional[Any] = None
    maximum: Optional[float] = None
    exclusiveMaximum: Optional[bool] = None
    minimum: Optional[float] = None
    exclusiveMinimum: Optional[bool] = None
    maxLength: Optional[int] = None
    minLength: Optional[int] = None
    pattern: Optional[str] = None
    maxItems: Optional[int] = None
    minItems: Optional[int] = None
    uniqueItems: Optional[bool] = None
    enum: Optional[list[Any]] = None
    multipleOf: Optional[float] = None


Headers = Dict[str, Header]


@dataclass
class Response(DataClassDictMixin):
    description: str
    schema: Optional[Union[Reference, Schema]] = None
    headers: Optional[Headers] = None


# TODO: does matrix ever use the default field?
# responses are never a reference in the matrix doc

# Sometimes wildcards such as 3xx are used as the key
# NOTE: mashumaro fails to properly parse this
Responses = Dict[Union[str,int], Response]





@dataclass
class Operation(DataClassDictMixin):
    responses: Responses
    tags: Optional[list[str]] = None
    summary: Optional[str] = None
    description: Optional[str] = None
    externalDocs: Optional[ExternalDocumentation] = None
    operationId: Optional[str] = None
    consumes: Optional[list[str]] = None  # overrides the global definition
    produces: Optional[list[str]] = None  # overrides the global definition
    parameters: Optional[list[Parameter]] = None
    schemes: Optional[list[str]] = None  # overrides the global definition
    deprecated: Optional[bool] = None
    security: Optional[List[SecurityRequirement]] = None
    #@classmethod
    #def __post_deserialize__(cls: Type[Operation], obj: Operation) -> Operation:
    #    print(obj.security)
    #    return obj

@dataclass
class PathItem(DataClassDictMixin):
    get: Optional[Operation] = None
    put: Optional[Operation] = None
    post: Optional[Operation] = None
    delete: Optional[Operation] = None
    options: Optional[Operation] = None
    head: Optional[Operation] = None
    patch: Optional[Operation] = None


# PathItems are never a reference in matrix spec
Paths = dict[str, PathItem]

class ParameterLocation(Enum):
    Body = "body"
    Query = "query"
    Header = "header"
    Path = "path"
    FormData = "formData"

# TODO: this should really be at least two classes, one of which being BodyParameter
@dataclass
class Parameter(DataClassDictMixin):
    name: str
    in_: ParameterLocation = field(metadata=field_options(alias="in"))
    description: Optional[str] = None
    required: Optional[bool] = None  # if _in==path => **must be true**, else optional and default is false
    schema: Optional[Schema] = None  # _should_ hold a value if _in==body

    # if _in is NOT body
    type: Optional[SwaggerDataType] = None  # technically _not_ Optional, but whatever
    format: Optional[str] = None  # "string", "number", "integer", "boolean", "array" or "file"
    allowEmptyValues: Optional[bool] = None

    items: Optional[Schema] = None  # never a ref


ParametersDefinitions = Dict[str, Parameter]


@dataclass
class SecurityScheme(DataClassDictMixin):
    name: str
    in_: str = field(metadata=field_options(alias="in"))# "query" or "header"
    type: str  # "basic", "apiKey" or "oauth2" create an enum when needed!
    description: Optional[str] = None


Definitions = Dict[str, Schema]
ResponsesDefinitions = Dict[str, Response]



#SecurityRequirement = Union[]

@dataclass
class HomeserverAccessSecurity(DataClassDictMixin):
    homeserverAccessToken: List # empty list

@dataclass
class AccessTokenSecurity(DataClassDictMixin):
    accessToken: List # empty list

SecurityRequirement = Union[HomeserverAccessSecurity, AccessTokenSecurity] #Dict[str, list[str]]
SecurityDefinitions = Dict[str, SecurityScheme]


@dataclass
class ExternalDocumentation(DataClassDictMixin):
    url: str
    description: Optional[str] = None


@dataclass
class Contact(DataClassDictMixin):
    name: Optional[str] = None
    url: Optional[str] = None
    email: Optional[str] = None


@dataclass
class License(DataClassDictMixin):
    name: str
    url: Optional[str] = None


@dataclass
class Info(DataClassDictMixin):
    title: str
    version: str
    #description: Optional[str] = None
    #termsOfService: Optional[str] = None
    #contact: Optional[Contact] = None
    #license: Optional[License] = None



@dataclass
class Tag(DataClassDictMixin):
    name: str
    description: Optional[str] = None
    externalDocs: Optional[ExternalDocumentation] = None


@dataclass
class Swagger(DataClassYAMLMixin):
    swagger: str  # TODO: always 2.0?
    info: Info
    paths: Paths
    host: Optional[str] = None
    basePath: Optional[str] = None
    schemes: Optional[list[str]] = None  # limited to "http", "https", "ws", "wss"
    consumes: Optional[list[str]] = None
    produces: Optional[list[str]] = None
    ## not used: definitions: Optional[Definitions] = None
    parameters: Optional[ParametersDefinitions] = None
    responses: Optional[ResponsesDefinitions] = None
    #securityDefinitions: Optional[Reference] = None
    security: Optional[list[SecurityRequirement]] = None
    tag: Optional[list[Tag]] = None
    externalDocs: Optional[ExternalDocumentation] = None
    ## extensions ignored

    defined_in_path: str = "PLACEHOLDER" # will be overriden in the post_deserialize hook

    @classmethod
    def __post_deserialize__(cls: Type[Swagger], obj: Swagger) -> Swagger:
        ctx = get_parse_context()
        assert ctx.swagger_path, "failed to set swagger path"
        obj.defined_in_path = ctx.swagger_path
        return obj


# TODO: _almost all_ references point to a schema, except security-definitions that it :/
RefPathLookup = Dict[str, Schema]

@dataclass
class SwaggerEventSchema:
    schema: Schema
    path: str

#wrapper to a list, for future additions
@dataclass
class SwaggerEventSchemas:
    schemas: List[SwaggerEventSchema]

@dataclass
class SwaggerData:
    ref_path_lookup: RefPathLookup
    categories: List[ApiCategory]
    event_schemas: SwaggerEventSchemas



class ApiCategoryEnum(StrEnum):
    ClientServer = "client-server"
    ApplicationService = "application-service"
    PushGateway = "push-gateway"
    Identity = "identity"
    ServerServer = "server-server"

def match_dir_to_api(dir_path: str) -> ApiCategoryEnum:
    dirname = os.path.dirname(dir_path)
    return ApiCategoryEnum(dirname)


#class SchemaCategoryEnum(StrEnum):
#    EventSchemas = "event-schemas"
#    OtherSchemas = "schemas"
#
#@dataclass
#class SchemaCategory:
#    type: SchemaCategoryEnum
#    schemas: List[Schema]


@dataclass
class ApiCategory:
    type: ApiCategoryEnum
    swaggers: List[Swagger]
    

def load_swagger(path: str) -> Swagger:
    ctx = get_parse_context()
    assert ctx.spec_base_path, "what"
    ctx.swagger_path = resolve_path(ctx.spec_base_path, path)
    with open(path) as so:
        yaml_data = so.read()
        swagger = Swagger.from_yaml(yaml_data)
        return swagger

def load_schema_reference(ctx: ParseContext, path: str) -> Schema:
    assert ctx.spec_base_path
    ctx.swagger_path = resolve_path(ctx.spec_base_path, path)
    with open(path) as sf:
        yaml_data = sf.read()
        schema = Schema.from_yaml(yaml_data)
        return schema

def load_api_path_swaggers(api_path: str) -> List[Swagger]:
    swagger_paths = glob(os.path.join(api_path, "*.yaml"))
    swaggers = [load_swagger(swagger_path) for swagger_path in swagger_paths]
    return swaggers

@dataclass
class CategoryPathTuple:
    category: ApiCategoryEnum
    path: str

def get_category_path_tuples(base_path: str) -> List[CategoryPathTuple]:
    api_path = os.path.join(base_path, "api")
    assert os.path.isdir(api_path), f"no api folder found in given path {base_path}"

    # TODO go through all ... eventually
    #categories =  [cat for cat in ApiCategoryEnum]
    categories =  [ApiCategoryEnum.ClientServer, ApiCategoryEnum.ApplicationService, ApiCategoryEnum.PushGateway, ApiCategoryEnum.Identity]
    category_path_tuples = [CategoryPathTuple(cat,os.path.join(api_path, str(cat))) for cat in categories]
    for tuple in category_path_tuples:
        assert os.path.isdir(tuple.path), f"api category {tuple.category} of path {tuple.path} is not a folder"
    return category_path_tuples

def accumulate_ref_lookup(ctx: ParseContext) -> RefPathLookup:
    ref_acc: Set[str] = set()

    ref_path_lookup = {}
    # accumulate new references while new ones are found
    while True:
        diff = ctx.refs - ref_acc
        if len(diff) == 0:
            break
        ref_acc = ref_acc.union(diff)
        ctx.refs = set() #reset
        new_lookup = {path: load_schema_reference(ctx,path) for path in diff}
        ref_path_lookup |= new_lookup
    return ref_path_lookup

def init_ctx(spec_base_path: str) -> ParseContext:
    ctx = get_parse_context()
    ctx.swagger_path = None
    ctx.refs = set()
    ctx.spec_base_path = spec_base_path
    return ctx

def load_event_schemas(ctx: ParseContext, base_path: str) -> SwaggerEventSchemas:
    schema_folder_path = os.path.join(base_path, "event-schemas", "schema")
    assert os.path.isdir(schema_folder_path), f"path {schema_folder_path} "
    schema_paths = glob(os.path.join(schema_folder_path, "*.yaml"))
    schemas = [SwaggerEventSchema(load_schema_reference(ctx, path), path) for path in schema_paths]

    event_schemas = SwaggerEventSchemas(
        schemas=schemas,
    )
    return event_schemas


def load_swaggers_from_path(base_path: str) -> SwaggerData:
    category_path_tuples = get_category_path_tuples(base_path)

    ctx = init_ctx(base_path)

    api_categories = [
        ApiCategory(
            type=cat_tuple.category,
            swaggers= load_api_path_swaggers(cat_tuple.path)
        )
        for cat_tuple in category_path_tuples
    ]

    event_schemas = load_event_schemas(ctx, base_path)

    ref_path_lookup = accumulate_ref_lookup(ctx)


    return SwaggerData(
        ref_path_lookup=ref_path_lookup,
        categories=api_categories,
        event_schemas=event_schemas,
    )
