import play.PlayJava

name := """katangapp-backend"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

resolvers += "Maven Local Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository"

resolvers += "jitpack" at "https://jitpack.io"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "com.google.inject" % "guice" % "4.0",
  "com.github.craftsmanship-toledo" % "katangapp-api" % "katangapp-api-1.6.1",
  "org.jsoup" % "jsoup" % "1.8.3"
)

findbugsSettings

findbugsExcludeFilters := Some(scala.xml.XML.loadFile(baseDirectory.value / "findbugs-exclude-filters.xml"))
