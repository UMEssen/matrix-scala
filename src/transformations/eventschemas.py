import os
from pprint import pprint
from typing import cast
from codegen.data import ContainerModel, EventSchemas, ModelType, PolymorphicModel
from swagger import RefPathLookup, SwaggerEventSchema, SwaggerEventSchemas
from transformations.data import MutableContext
from transformations.types import transform_schema
from transformations.util import to_model_name

# analagoues to the one from definition
# but I would like to escape the name of a schema, e.g: `m̀.room.message$m.text`
def suggested_name_for_event_schema(schema: SwaggerEventSchema) -> str:
    base_name = os.path.basename(schema.path)
    file_name, _ = os.path.splitext(base_name)
    suggested_name = to_model_name(file_name)
    return suggested_name

def transform_event_schemas(ctx: MutableContext, swagger_schemas: SwaggerEventSchemas) -> EventSchemas:
    container = ContainerModel(model_name="EventSchemas").to_polymorphic()
    #ctx = MutableContext(
    #    ref_lookup=ref_path_lookup,
    #    model_pool=[],
    #    definitions_container=container,
    #    definition_cache={},
    #)
    transformed_types = [
        (transform_schema(ctx, ses.schema, suggested_name_for_event_schema(ses), container), suggested_name_for_event_schema(ses))
        for ses in swagger_schemas.schemas
    ]

    assert all([t[0].is_model for t in transformed_types]), "non model as event-schema"

    for t in transformed_types:
        cast(ModelType, t[0].data).model.data.model_name = t[1] 

    #models = [cast(ModelType, t.data).model for t in transformed_types]

    return EventSchemas(
        container=container,
    )

