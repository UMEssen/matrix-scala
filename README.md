# Matrix Scala
<a href="https://matrix.org/?_target=blank"><img src="made-for-matrix.png" width="250px"></a>


## Description
A scala library automatically generated from the [matrix specification]()

**This library is NOT**:
<br>
A fully fletched client with batteries included(caching, highlevel abstraction, automatic de-/ encryption,...).
<br>
A full client is planned for the future, as a seperate library

**This library is:**
<br>
a simple interface to the matrix api. With _just enough_ to build an actual client.<br>
It provides strong typing, api calls to the server, automatic JSON (de/)serialization

## Content
This library encapsulates:
- [the client server api](https://spec.matrix.org/v1.6/client-server-api/)
- [the push-gateway api](https://spec.matrix.org/v1.6/push-gateway-api/)
- [the application service api](https://spec.matrix.org/v1.6/application-service-api/)
- [the identity service api](https://spec.matrix.org/v1.6/identity-service-api/)
- [events used in the specification](https://spec.matrix.org/v1.6/client-server-api/#events)
- An ApiInvoker which handles request serialization and response deserialization

## Examples
Some basic examples are provided: [examples](examples)

## Instructions to build
A prebuilt is provided: [build/matrix-scala](build/matrix-scala)
```shell
mkdir build
# retrieve the matrix specification. Replace it with a fork if desired
git clone git@github.com:matrix-org/matrix-spec.git build/matrix-spec

# generate it
python src/main.py \
    --spec-path ./build/matrix-spec/data/ \
    --build-config ./build_config.yaml \
    --output ./build/matrix-scala \
    --templates ./templates 

cd ./build/matrix-scala/

# use scalafmt to make it pretty
sbt scalafmtAll

# compile it
sbt compile

# for local testing
sbt publishLocal

cd -
```

## Further reading
- https://matrix.org/
- https://spec.matrix.org/v1.6/
- https://gitlab.matrix.org/matrix-org/olm
- https://matrix.org/docs/guides/client-server-api
- https://matrix.org/docs/develop

## TODO
- Integration of [Olm](https://gitlab.matrix.org/matrix-org/olm) via JNI for E2EE
- File up- / download
- Login + different authentication types
- a pom.xml for maven
- the server-server api (low priority)
## Contributors
- [Kirill Sokol](mailto:kirill.sokol@uk-essen.de)