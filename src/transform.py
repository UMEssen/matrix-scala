from codegen.data import ContainerModel, Module
from swagger import SwaggerData, RefPathLookup
from transformations.category import transform_to_category
from transformations.data import MutableContext, TransformationResult
from transformations.eventschemas import transform_event_schemas
from transformations.module import transform_to_module
from dataclasses import dataclass

def transform_swaggers(data: SwaggerData) -> TransformationResult:
    definitions_container = ContainerModel(
        parent=None,
        model_name="Definitions"
    ).to_polymorphic()
    ctx = MutableContext(
        ref_lookup=data.ref_path_lookup,
        definitions_container=definitions_container,
        definition_cache={},
        model_pool=[],
    )
    categories = [
        transform_to_category(ctx, swagger_category)
        for swagger_category in data.categories
    ]

    event_schemas = transform_event_schemas(ctx, data.event_schemas)

    return TransformationResult(
        definitions_container=definitions_container,
        categories=categories,
        event_schemas=event_schemas,
    )
