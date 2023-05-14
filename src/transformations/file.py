from typing import Optional

from codegen.data import GenericType, PolymorphicType, PrimitiveType, PrimitiveTypeEnum
from swagger import FormatEnum, Schema, SwaggerDataType
from transformations.data import MutableContext


def is_schema_file(schema: Schema) -> bool:
    return schema.type == SwaggerDataType.File or schema.format == FormatEnum.Byte

def transform_schema_as_file(ctx: MutableContext, schema: Schema) -> PolymorphicType:
    assert is_schema_file(schema)
    print(f"TODO: file arguments are not supported for now! Fallback to generic model. Origin: {schema.defined_in_path}")
    type = GenericType().to_polymorphic()
    return type
