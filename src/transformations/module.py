import os
from swagger import Swagger
from transformations.data import MutableContext
from transformations.operation import transform_to_operations
from transformations.util import to_model_name
from codegen.data import Module

# a hacky extraction form swagger.info.title
# a possible alternative could be an extraction from a path's tag
#SWAGGER_MODULE_PREFIX = "Matrix Client-Server "
def get_module_name(swagger: Swagger) -> str:
    #assert swagger.info
    #assert swagger.info.title
    #info_title = swagger.info.title
    #assert swagger.defined_in_path != "PLACEHOLDER", "fail"
    #assert info_title.startswith(SWAGGER_MODULE_PREFIX)
    base_name = os.path.basename(swagger.defined_in_path)
    file_name, _ = os.path.splitext(base_name)
    name = to_model_name(file_name)
    return name 


def transform_to_module(ctx: MutableContext, swagger: Swagger) -> Module:
    module_name = get_module_name(swagger)
    module = Module(
            module_name=module_name,
            operations=transform_to_operations(ctx, swagger),
            defined_in_path=swagger.defined_in_path,

        )
    return module
