from codegen.data import GenerationApiCategory
from swagger import ApiCategory
from transformations.data import MutableContext
from transformations.module import transform_to_module


def transform_to_category(ctx: MutableContext,swagger_category: ApiCategory) -> GenerationApiCategory:
    modules = [
        transform_to_module(ctx, swagger)
        for swagger in swagger_category.swaggers
    ]

    category = GenerationApiCategory(
        category=swagger_category.type,
        modules=modules,
        display_name=GenerationApiCategory.display_name_from_type(swagger_category.type),
    )
    return category

