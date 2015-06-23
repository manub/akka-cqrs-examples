name := "akka-cqrs-examples"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(

  "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.11",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0-RC3",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-RC3",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0-RC3",
  "de.heikoseeberger" %% "akka-http-play-json" % "0.8.0", // or akka-http-json4s

  "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0-RC3" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"


)