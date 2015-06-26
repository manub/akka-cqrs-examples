name := "akka-cqrs-social-network"

version := "1.0"

lazy val commonSettings = Seq(
  scalaVersion := "2.11.7",
  organization := "net.manub"
)

resolvers += "dnvriend at bintray" at "http://dl.bintray.com/dnvriend/maven"

libraryDependencies ++= Seq(

  "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.11",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0-RC4",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0-RC4",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0-RC4",
  "de.heikoseeberger" %% "akka-http-play-json" % "0.9.1",

  "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0-RC4" % "test",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test,it",
  "com.github.dnvriend" %% "akka-persistence-inmemory" % "1.0.3" % "test,it"
)

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(commonSettings: _*)
  .settings(Defaults.itSettings: _*)

parallelExecution in IntegrationTest := false