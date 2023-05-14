import os
from codegen.data import PolymorphicType
from swagger import Reference, Schema
from transformations.data import MutableContext
from transformations.util import to_model_name

def get_suggested_name_from_ref(ref: Reference) -> str:
    base_name = os.path.basename(ref.ref)
    file_name, _ = os.path.splitext(base_name)

    suggested_name = to_model_name(file_name)
    return suggested_name

def transform_schema_as_definition(ctx: MutableContext, ref: Reference) -> PolymorphicType:
    from transformations.types import transform_schema # avoid circular import


    # HACK: we should cache by path, not some arbitrary generated value
    cache_attempt = ctx.definition_cache.get(ref.ref)
    if cache_attempt:
        return cache_attempt
    schema = ctx.ref_lookup.get(ref.ref)
    assert schema, "invalid ref lookup"
    suggested_name = get_suggested_name_from_ref(ref)
    #print("cache miss for: ",suggested_name, "=>", ref.ref)
    ptype = transform_schema(ctx, schema, suggested_name, ctx.definitions_container) 

    # add to cache
    ctx.definition_cache[ref.ref] = ptype
    return ptype
