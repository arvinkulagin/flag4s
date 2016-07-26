lazy val root = (project in file(".")).
  settings(
    name := "flag4s",
    version := "0.1",
    scalaVersion := "2.11.8",
    libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
    assemblyJarName in assembly := "flag4s.jar"
  )