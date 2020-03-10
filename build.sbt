name := "kafka-assignment"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.apache.kafka" %% "kafka" % "2.1.0"

libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % Test

libraryDependencies += "net.manub" %% "scalatest-embedded-kafka" % "0.14.0" % "test"
