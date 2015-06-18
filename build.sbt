name := "simple-akka-kafka-cqrs"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(

  "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.11",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0-RC3",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-RC3",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0-RC3",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0-RC3",

  "org.scalatest" %% "scalatest" % "2.2.4" % "test"


)