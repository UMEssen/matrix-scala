
from dataclasses import dataclass
from pprint import pp, pprint
from typing import Callable, Dict, List, Optional, Union, cast
from itertools import accumulate
from codegen.data import Argument, Composition, DataModel, GenericValueType, ModelType, OptionType, PolymorphicModel, PrimitiveType, PrimitiveTypeEnum, is_reserved
from swagger import Reference, Schema
from transformations.composition import detect_transitionary_composite, transform_composites, traverse_transitory_composite
from transformations.data import MutableContext, PolymorphicType
from transformations.util import PLACEHOLDER, attach_model, flatten_2d, to_model_name, wrap_in_option


def is_schema_model(schema: Schema) -> bool:
    return (schema.properties is not None) or (schema.allOf is not None)


@dataclass
class FieldTransformation:
    arg: Argument

def transform_model_field(ctx: MutableContext, field_name: str, field_schema: Union[Schema, Reference], is_required: bool,  model: PolymorphicModel) -> FieldTransformation:
    from transformations.types import transform_schema # avoid circular import
    t = wrap_in_option(is_required, transform_schema(ctx, field_schema, to_model_name(field_name), model))
    
    arg = Argument(
        name=field_name,
        type=t,
        description=None,
        required=PLACEHOLDER(True),
        escape=is_reserved(field_name), 
    )
    return FieldTransformation(
        arg=arg,
    )

def transform_model_fields(ctx: MutableContext, properties: Dict[str, Union[Schema, Reference]], required: List[str],model: PolymorphicModel) -> List[FieldTransformation]:
    return [
        transform_model_field(ctx, field_name, field_schema, field_name in required,model)
        for (field_name, field_schema) in properties.items()
    ]

def attempt_additional_type(ctx: MutableContext, schema: Schema, parent: PolymorphicModel) -> Optional[PolymorphicType]:
    from transformations.types import transform_schema # avoid circular import
    if schema.additionalProperties is None:
        return None

    if isinstance(schema.additionalProperties, bool):
        if not schema.additionalProperties:
            # why specify additionalProperties just to say: "Nope, no fields for you"
            return None
        return GenericValueType().to_polymorphic()
    if isinstance(schema.additionalProperties, Union[Schema, Reference]):
        return transform_schema(ctx, schema.additionalProperties, "Additional", parent)
    
    raise Exception("reached unreachable")


def transform_schema_as_model(ctx: MutableContext, schema: Schema, suggested_name: str, parent: Optional[PolymorphicModel]) -> PolymorphicType:
    assert is_schema_model(schema)
    properties = schema.properties or {}

    model_name = to_model_name(schema.title) if schema.title is not None else to_model_name(suggested_name)
    model = DataModel(
        model_name=model_name,
        description=schema.description,
        defined_in_path=schema.defined_in_path,
    )
    pmodel = model.to_polymorphic()
    field_transformations = transform_model_fields(ctx, properties, schema.required or [], pmodel)
    model.fields = [
        transformation.arg 
        for transformation in field_transformations
    ]

    additional_type = attempt_additional_type(ctx, schema, pmodel)
    if additional_type is not None:
        model.additional_type = additional_type

    composites = transform_composites(ctx, [ t.arg.name for t in field_transformations],schema, pmodel)
    if composites:
        if detect_transitionary_composite(schema, composites):
            return traverse_transitory_composite(composites[0])
        model.compositions = composites

    model_type = ModelType(
        model=pmodel
    ).to_polymorphic()
    if parent:
        attach_model(pmodel, parent)
    return model_type
