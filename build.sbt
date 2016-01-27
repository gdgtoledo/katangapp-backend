name := """katangapp-backend"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

resolvers += Resolver.mavenLocal

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.google.inject" % "guice" % "4.0",
  "es.craftsmanship.toledo" % "katanga-api" % "1.0",
  "org.jsoup" % "jsoup" % "1.8.3"
)

findbugsSettings

findbugsExcludeFilters := Some(scala.xml.XML.loadFile(baseDirectory.value / "findbugs-exclude-filters.xml"))
