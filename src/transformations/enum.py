from os import name
from typing import Optional

from codegen.data import GenericType, MapType, ModelType, PolymorphicModel, PolymorphicType, StringEnumModel
from swagger import Schema, SwaggerDataType
from transformations.data import MutableContext
from transformations.util import attach_model, to_model_name


def is_schema_enum(schema: Schema) -> bool:
    return schema.enum is not None

def transform_schema_as_enum(ctx: MutableContext, schema: Schema, suggested_name: str, parent: Optional[PolymorphicModel]) -> PolymorphicType:
    assert is_schema_enum(schema)
    assert schema.enum is not None

    if(schema.type != SwaggerDataType.String):
        assert False, "only stringy enums are supported for now"

    values = schema.enum
    
    enum_name = to_model_name(schema.title) if schema.title else to_model_name(suggested_name+"Enum")
    enum_model = StringEnumModel(
        model_name=enum_name,
        values = values,
    ).to_polymorphic()

    if parent:
        attach_model(enum_model, parent)

    model_type = ModelType(
        model= enum_model,
    ).to_polymorphic()
    return model_type
