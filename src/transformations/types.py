from typing import Optional, Union, cast
from transformations.array import is_schema_array, transform_schema_as_array
from transformations.definition import transform_schema_as_definition
from transformations.enum import is_schema_enum, transform_schema_as_enum
from transformations.file import is_schema_file, transform_schema_as_file
from transformations.generic import is_schema_generic, transform_schema_as_generic
from transformations.map import is_schema_map, transform_schema_as_map
from transformations.model import is_schema_model, transform_schema_as_model
from transformations.primitive import is_schema_primitive, transform_schema_as_primitive
from transformations.union import is_schema_union, transform_schema_as_union
from transformations.util import to_model_name

from codegen.data import PolymorphicModel, PolymorphicType
from swagger import Reference, Schema
from transformations.data import MutableContext



def is_schema_reference(schema: Union[Schema,Reference]) -> bool:
    return isinstance(schema, Reference)


# suggested_name is used when there is no provided name
def transform_schema(ctx: MutableContext, schema: Union[Schema,Reference], suggested_name: str, parent: Optional[PolymorphicModel]) -> PolymorphicType:
    # TODO: make this more functional
    # I would like each transformer to return a Optional
    # there should be a list of transformers
    # this function should try each transformer and return the first match

    if is_schema_reference(schema): 
        return transform_schema_as_definition(ctx, cast(Reference,schema)  )

    schema = cast(Schema, schema)

    if is_schema_union(schema):
        return transform_schema_as_union(ctx, schema, suggested_name, parent)

    if is_schema_enum(schema):
        return transform_schema_as_enum(ctx, schema, suggested_name, parent)

    if is_schema_file(schema):
        return transform_schema_as_file(ctx, schema)

    if is_schema_primitive(schema):
        return transform_schema_as_primitive(ctx, schema)

    if is_schema_map(schema):
        return transform_schema_as_map(ctx, schema, suggested_name, parent)

    if is_schema_model(schema):
        return transform_schema_as_model(ctx, schema, suggested_name, parent)

    if is_schema_array(schema):
        return transform_schema_as_array(ctx, schema, suggested_name, parent)

    if is_schema_generic(schema):
        return transform_schema_as_generic(ctx, schema)

    raise Exception("failed to transform schema")

