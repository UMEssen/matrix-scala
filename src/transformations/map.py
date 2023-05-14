from typing import Optional, Union

from codegen.data import MapType, PolymorphicModel, PolymorphicType
from swagger import  Reference, Schema
from transformations.data import MutableContext


def is_schema_map(schema: Schema) -> bool:
    return isinstance(schema.additionalProperties, Union[Schema, Reference]) and schema.properties is None and schema.allOf is None

def transform_schema_as_map(ctx: MutableContext, schema: Schema, suggested_name: str, parent: Optional[PolymorphicModel]) -> PolymorphicType:
    assert is_schema_map(schema)
    assert isinstance(schema.additionalProperties, Union[Schema,Reference])
    
    from transformations.types import transform_schema # avoid circular import
    value_type = transform_schema(ctx, schema.additionalProperties, suggested_name, parent)

    map_type = MapType(
        inner_type=value_type,
    ).to_polymorphic()
    return map_type
