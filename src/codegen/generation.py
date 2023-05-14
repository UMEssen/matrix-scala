from dataclasses import dataclass
import os
from typing import Any, Callable, Dict, Generic, Iterable, List, Optional, TypeVar
from jinja2 import Environment, FileSystemLoader
from mashumaro.mixins.yaml import DataClassYAMLMixin
from codegen.data import GenerationApiCategory, Module

from transformations.data import TransformationResult
from transformations.util import flatten_2d

T = TypeVar("T")

@dataclass
class PackageConfig(DataClassYAMLMixin):
    version: str
    project_name: str
    package_base: str
    author: str
    group_id: str
    scala_version: str
    akka_http_version: str
    akka_version: str
    spray_json_version: str
    scalafmt_version: str

@dataclass
class GenerationConfig:
    package_config: PackageConfig
    template_path: str
    ouput_path: str

@dataclass
class GenerationEnvironment:
    package_config: PackageConfig
    #generate_id = ...


Location = List[str]

@dataclass
class GenerationTarget(Generic[T]):
    template_file: str
    output_location: Location
    file_name: Callable[[T],str]
    iterable: Iterable[T]
    additional: Optional[Dict[str, Any]] = None


def OneTimeGeneration(template_file: str, output_location: Location, file_name: str, data: Optional[T] = None, additional: Optional[Dict[str, Any]]= None) -> GenerationTarget[Optional[T]]:
    return GenerationTarget[Optional[T]](
        template_file=template_file,
        output_location=output_location,
        file_name=lambda _:file_name,
        iterable=[data], # dummy entry
        additional=additional,
    )

@dataclass
class DummyDataclass:
    pass

def generate_targets(config: GenerationConfig, env: GenerationEnvironment,targets: List[GenerationTarget]):
    os.makedirs(config.ouput_path, exist_ok=True)
    j2_loader = FileSystemLoader(config.template_path)
    j2_env = Environment(loader=j2_loader)
    for target in targets:
        base_path = os.path.join(config.ouput_path, *target.output_location)
        os.makedirs(base_path, exist_ok=True)

        for data in target.iterable:
            merged=(data or DummyDataclass()).__dict__ | env.__dict__ | (target.additional or {}) #HACK
            output_path = os.path.join(base_path, target.file_name(data))
            print(f"generating {output_path}")
            with open(output_path, "w") as of:
                template = j2_env.get_template(target.template_file)
                rendered = template.render(data=merged)
                of.write(rendered)

def package_name_to_location(pkg: str) -> Location:
    return pkg.split(".")

def build_category_targets(package_location: Location, category: GenerationApiCategory) -> List[GenerationTarget]:
    additional = {"category": category}
    return [
        GenerationTarget[Module](
            template_file="data_module.j2",
            output_location=[*package_location, "model", category.display_name],
            file_name=lambda m: f"{m.module_name}Data.scala",
            iterable=category.modules,
            additional=additional,
        ),
        GenerationTarget[Module](
            template_file="api_module.j2",
            output_location=[*package_location, "api", category.display_name],
            file_name=lambda m: f"{m.module_name}Api.scala",
            iterable=category.modules,
            additional=additional,
        ),
        GenerationTarget[Module](
            template_file="json_module.j2",
            output_location=[*package_location, "json",category.display_name],
            file_name=lambda m: f"{m.module_name}JsonFormats.scala",
            iterable=category.modules,
            additional=additional,
        ),
    ]

def generate_project(config: GenerationConfig, data: TransformationResult):
    env = GenerationEnvironment(
            package_config=config.package_config
    )
    root = []# just so it is more readable
    package_location = [*root,"src","main", "scala", *package_name_to_location(config.package_config.package_base)]
    targets: List[GenerationTarget] = [
        OneTimeGeneration(
            template_file="README.md.j2",
            output_location=[*root,],
            file_name="README.md"
        ),
        OneTimeGeneration(
            template_file="build.sbt.j2",
            output_location=[*root,],
            file_name="build.sbt"
        ),
        OneTimeGeneration(
            template_file="plugins.sbt.j2",
            output_location=[*root,"project"],
            file_name="plugins.sbt"
        ),
        OneTimeGeneration(
            template_file="scalafmt.conf.j2",
            output_location=[*root,],
            file_name=".scalafmt.conf"
        ),
        OneTimeGeneration(
            template_file="ApiCore.scala.j2",
            output_location=[*package_location,"core"],
            file_name="ApiCore.scala"
        ),
        OneTimeGeneration(
            template_file="QueryBuilding.scala.j2",
            output_location=[*package_location,"core"],
            file_name="QueryBuilding.scala"
        ),
        OneTimeGeneration(
            template_file="HeaderBuilding.scala.j2",
            output_location=[*package_location,"core"],
            file_name="HeaderBuilding.scala"
        ),
        OneTimeGeneration(
            template_file="InvokerActor.scala.j2",
            output_location=[*package_location,"core"],
            file_name="InvokerActor.scala"
        ),
        OneTimeGeneration(
            template_file="KnownMatrixErrors.scala.j2",
            output_location=[*package_location,"core"],
            file_name="KnownMatrixErrors.scala"
        ),
        OneTimeGeneration(
            template_file="Authentication.scala.j2",
            output_location=[*package_location,"core"],
            file_name="Authentication.scala"
        ),
        OneTimeGeneration(
            template_file="CoreJsonFormats.scala.j2",
            output_location=[*package_location,"json","core"],
            file_name="CoreJsonFormats.scala"
        ),
        OneTimeGeneration(
            template_file="KnownErrorFormats.scala.j2",
            output_location=[*package_location,"json","core"],
            file_name="KnownErrorFormats.scala"
        ),
        OneTimeGeneration(
            template_file="event_schemas_data.j2",
            output_location=[*package_location,"model"],
            file_name="EventSchemas.scala",
            data=data.event_schemas.container,
        ),
        OneTimeGeneration(
            template_file="Definitions.scala.j2",
            output_location=[*package_location,"model"],
            file_name="Definitions.scala",
            data=data.definitions_container
        ),
        OneTimeGeneration(
            template_file="json_definitions.j2",
            output_location=[*package_location,"json"],
            file_name="DefinitionFormats.scala",
            data=data.definitions_container
        ),
        OneTimeGeneration(
            template_file="json_eventschemas.j2",
            output_location=[*package_location,"json"],
            file_name="EventSchemaFormats.scala",
            data=data.event_schemas.container,
        ),
        *flatten_2d([
            build_category_targets(package_location, category)
            for category in data.categories
        ])
    ]
    generate_targets(config, env,targets)
