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
  "com.github.craftsmanship-toledo" % "katangapp-api" % "katangapp-api-1.7.1",
  "org.jsoup" % "jsoup" % "1.8.3",
  // swagger dependencies
  "com.wordnik" %% "swagger-play2" % "1.3.12" exclude("org.reflections", "reflections"),
  "org.reflections" % "reflections" % "0.9.8" notTransitive (),
  "org.webjars" % "swagger-ui" % "2.1.8-M1"
)

findbugsSettings

findbugsExcludeFilters := Some(scala.xml.XML.loadFile(baseDirectory.value / "findbugs-exclude-filters.xml"))
