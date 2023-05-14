from pprint import pp, pprint
import swagger
import argparse
from codegen.generation import GenerationConfig, PackageConfig, generate_project
from transform import transform_swaggers

def load_package_config(path: str) -> PackageConfig:
    with open(path, "r") as file:
        return PackageConfig.from_yaml(file.read())

if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        prog = "matrix-scala-generator",
        description= "Matrix Client-Server Spec => Scala Library"
    )
    parser.add_argument("--spec-path", required=True, help="path to the Client-Server Matrix specification")
    parser.add_argument("--build-config", required=True, help="path to a build configuration")
    parser.add_argument("--output", required=True, help="output path")
    parser.add_argument("--templates", required=True, help="path to templates directory")
    #parser.add_argument("--apis", required=False, nargs='+', help="manual list of api paths")
    args = parser.parse_args()

    swagger_data = swagger.load_swaggers_from_path(args.spec_path)#, args.apis)
    transform_data = transform_swaggers(swagger_data)
    package_config = load_package_config(args.build_config)
    gen_config = GenerationConfig(
        package_config=package_config,
        template_path=args.templates,
        ouput_path=args.output,
    )
    generate_project(gen_config, transform_data)
