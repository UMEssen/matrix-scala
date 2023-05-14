from typing import List
from codegen.data import EventSchemas, GenerationApiCategory, PolymorphicModel, Module, PolymorphicType
from swagger import RefPathLookup

from dataclasses import dataclass


@dataclass
class TransformationResult:
    definitions_container: PolymorphicModel
    categories: List[GenerationApiCategory]
    event_schemas: EventSchemas


#@dataclass
#class TypeTuple:
#    determined_type: PolymorphicType
#    transformed_models: List[PolymorphicModel]

@dataclass
class MutableContext:
    ref_lookup: RefPathLookup

    # create a global pool for all models
    # useful for optimizations
    model_pool: list[PolymorphicModel]
    definitions_container: PolymorphicModel
    definition_cache: dict[str, PolymorphicType]
    
    def add_to_pool(self, model: PolymorphicModel) -> PolymorphicModel:
        self.model_pool.append(model)
        return model

