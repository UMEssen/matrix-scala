
from dataclasses import dataclass
from itertools import accumulate
from typing import Callable, List, Optional, cast
from codegen.data import Composition, DataModel, ModelType, PolymorphicModel, PolymorphicType
from swagger import Reference, Schema
from transformations.data import MutableContext
from transformations.generic import is_schema_generic


def detect_transitionary_composite(schema: Schema, compositions: List[Composition]) -> bool:
    if schema.allOf is None:
        return False
    return len(compositions) == 1 and schema.additionalProperties is None and schema.properties is None

def traverse_transitory_composite(composition: Composition) -> PolymorphicType:
    return ModelType(composition.origin).to_polymorphic()


@dataclass
class RemoveDuplicationIteration:
    visited: List[str]
    deduplicated_composition: Optional[Composition]# None for first iteration


def remove_composition_duplicates_iter(visited: List[str], composition: Composition) -> RemoveDuplicationIteration:
    new_fields =  [field for field in composition.fields if field.name not in visited]
    now_visited = [field.name for field in new_fields]

    deduplicated_composition = Composition(
        origin=composition.origin,
        fields=new_fields,
    )
    return RemoveDuplicationIteration(
        visited=visited+now_visited,
        deduplicated_composition=deduplicated_composition,
    )


# TODO: detect type changes, e.g.: when Composition1.a is a string and Composition2.a is a integer
# when a field is declared multiple times, then remove any duplicates
def remove_composition_duplicates(initial_keys: List[str], compositions: List[Composition]) -> List[Composition]:
    initial = RemoveDuplicationIteration(
        visited=initial_keys,
        deduplicated_composition=None, #DUMMY
    )
    accumulate_merger: Callable[[RemoveDuplicationIteration, Composition], RemoveDuplicationIteration] = lambda acc, comp: remove_composition_duplicates_iter(acc.visited, comp)
    iterations: List[RemoveDuplicationIteration] = list(
        accumulate(compositions, accumulate_merger, initial=initial)
    )[1:] # ignore initial value

    return [
        cast(Composition, i.deduplicated_composition)
        for i in iterations
    ]

@dataclass
class CompositionTuple:
    pmodel: PolymorphicModel # avoid to remake it a polymorphic
    data_model: DataModel

def transform_composition_tuple(ctx: MutableContext, tuple: CompositionTuple) -> Composition:
    composition = Composition(
        origin=tuple.pmodel,
        fields=tuple.data_model.fields,
    )
    return composition

def transform_composites(ctx: MutableContext, field_keys: List[str], schema: Schema, parent: PolymorphicModel) -> Optional[List[Composition]]:
    if schema.allOf is None:
        return None

    from transformations.types import transform_schema # avoid circular import
    transformations =  [
        transform_schema(ctx, composite_schema, "Composite", parent)
        for composite_schema in schema.allOf
        if isinstance(composite_schema, Reference) or (isinstance(composite_schema, Schema) and not is_schema_generic(composite_schema))
        #HACK: discard generic models, since the spec sometimes includes an example as an entry
    ]

    data_model_checks = [
        (
            t.is_model
            and isinstance(t.data, ModelType)
            and isinstance(t.data.model, PolymorphicModel)
            and isinstance(t.data.model.data, DataModel)
        )
        for t in transformations
        if not t.is_map #HACK ignore composition for additionalProperties for now
    ] 

    if any([t.is_map for t in transformations]):
        print(f"TODO: composition of additionalProperties is not supported for now, origin: {schema.defined_in_path}")

    assert all(data_model_checks)," non data-model as composite"

    tuples = [
        CompositionTuple(
            pmodel = (cast(PolymorphicModel,cast(ModelType, t.data).model)),
            data_model= cast(DataModel,(cast(PolymorphicModel,cast(ModelType, t.data).model)).data)
        )
        for t in transformations
        if not t.is_map #TODO remove this
    ]

    initial_compositions = [
        transform_composition_tuple(ctx, tuple)
        for tuple in tuples
    ]

    deduplicated_compositions = remove_composition_duplicates(field_keys, initial_compositions)
    return deduplicated_compositions

