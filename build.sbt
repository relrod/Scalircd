name := "scalircd"

version := "0.0.1"

scalaVersion := "2.10.0-M6"

scalacOptions += "-deprecation"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
 
libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor" % "2.1-M1",
  "com.typesafe.akka" % "akka-remote" % "2.1-M1"
)
