name := "test"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.+",
  "org.tinylog" % "tinylog" % "1.3.+",
  "org.tinylog" % "slf4j-binding" % "1.3.1"
)