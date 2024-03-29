import sbt._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "AndroidScammer",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "2.9.1",
    platformName in Android := "android-10"
  )

  val proguardSettings = Seq (
    useProguard in Android := true
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "alias_name",
      libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.RC1" % "test",
      libraryDependencies += "commons-net" % "commons-net" % "2.0"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "AndroidScammer",
    file("."),
    settings = General.fullAndroidSettings
  )

}
