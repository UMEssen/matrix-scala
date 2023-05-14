version := "0.0.1"
name := "logintesting"

scalaVersion := "2.13.7"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
  "de.ship" %% "matrix-scala" % "1.0.0",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.slf4j" % "slf4j-simple" % "1.7.5",

)
