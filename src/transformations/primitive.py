from typing import Dict, Optional
from codegen.data import PolymorphicType, PrimitiveType, PrimitiveTypeEnum
from swagger import FormatEnum, Schema, SwaggerDataType
from transformations.data import MutableContext

# TODO: either move this to the union transformer or make something better
primitive_mapping: Dict[SwaggerDataType, PolymorphicType] = {
    SwaggerDataType.String: PrimitiveType(type=PrimitiveTypeEnum.String).to_polymorphic(),
    SwaggerDataType.Integer: PrimitiveType(type=PrimitiveTypeEnum.Int).to_polymorphic(),
    SwaggerDataType.Boolean: PrimitiveType(type=PrimitiveTypeEnum.Boolean).to_polymorphic(),
    SwaggerDataType.Number: PrimitiveType(type=PrimitiveTypeEnum.Double).to_polymorphic(),
}

format_mapping = {
    FormatEnum.Int64 : PrimitiveType(PrimitiveTypeEnum.Int64).to_polymorphic(),
    FormatEnum.Int32 : PrimitiveType(PrimitiveTypeEnum.Int32).to_polymorphic(),
    FormatEnum.Float : PrimitiveType(PrimitiveTypeEnum.Float).to_polymorphic(),
}


def is_schema_primitive(schema: Schema) -> bool:
    return schema.type in primitive_mapping

def transform_schema_as_primitive(ctx: MutableContext, schema: Schema) -> PolymorphicType:
    assert is_schema_primitive(schema)
    assert isinstance(schema.type, SwaggerDataType)

    if schema.format is not None and (format_type := format_mapping.get(schema.format)) is not None:
        return format_type
    type = primitive_mapping[schema.type]
    return type 
