lazy val root = (project in file(".")).
  settings(
    name := "flag4s",
    version := "0.1",
    scalaVersion := "2.11.8",
    assemblyJarName in assembly := "flag4s.jar"
  )