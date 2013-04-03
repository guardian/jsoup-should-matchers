import java.util.jar._

organization := "com.gu"

name := "jsoup-should-matchers"

scalaVersion := "2.10.0"

version := "0.1"

publishArtifact := true

packageOptions <+= (version, name) map { (v, n) =>
  Package.ManifestAttributes(
    Attributes.Name.IMPLEMENTATION_VERSION -> v,
    Attributes.Name.IMPLEMENTATION_TITLE -> n,
    Attributes.Name.IMPLEMENTATION_VENDOR -> "guardian.co.uk"
  )
}

publishTo <<= (version) { version: String =>
    val publishType = if (version.endsWith("SNAPSHOT")) "snapshots" else "releases"
    Some(
        Resolver.file(
            "guardian github " + publishType,
            file(System.getProperty("user.home") + "/guardian.github.com/maven/repo-" + publishType)
        )
    )
}

libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.6.1",
  "org.scalatest" %% "scalatest" % "1.9.1"
)

