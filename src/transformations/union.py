from typing import List, Optional
from codegen.data import ContainerModel, GenericType, ModelType, OptionType, PolymorphicModel, PolymorphicType, PrimitiveType, PrimitiveTypeEnum, UnionType
from swagger import Schema, SwaggerDataType
from transformations.data import MutableContext
from transformations.util import attach_model, to_model_name
from transformations.primitive import primitive_mapping


def is_schema_union(schema: Schema) -> bool:
    return isinstance(schema.type,List)

union_mapping = primitive_mapping | {SwaggerDataType.Object: GenericType().to_polymorphic()}

def types_to_union(non_null_types: List[SwaggerDataType]) -> PolymorphicType:
    types: List[PolymorphicType] = [union_mapping[type] for type in non_null_types]
    return UnionType(types).to_polymorphic()


def transform_schema_as_union(ctx: MutableContext, schema: Schema, _suggested_name: str, _parent: Optional[PolymorphicModel]) -> PolymorphicType:
    assert is_schema_union(schema)
    assert isinstance(schema.type,List)
    types = schema.type
    assert len(types) > 0, "empty type detected"
    is_nullable = SwaggerDataType.Null in types
    non_null_types = [type for type in types if type != SwaggerDataType.Null]

    # avoid a union for a singular type
    type = types_to_union(non_null_types) if len(non_null_types) > 1 else union_mapping[non_null_types[0]]  

    if is_nullable:
        return OptionType(type).to_polymorphic()
    else:
        return type
