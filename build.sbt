import Libraries.android._
import Libraries.macroid._
import Libraries.graphics._
import Libraries.test._
import android.Keys._

android.Plugin.androidBuild

platformTarget in Android := Versions.androidPlatformV

name := "functional-view-android"

organization := "com.fortysevendeg"

organizationName := "47 Degrees"

organizationHomepage := Some(new URL("http://47deg.com"))

version := Versions.appV

scalaVersion := Versions.scalaV

scalacOptions ++= Seq("-feature", "-deprecation")

resolvers ++= Settings.resolvers

libraryDependencies ++= Seq(
  aar(macroidRoot),
  aar(androidDesign),
  aar(androidCardView),
  aar(androidRecyclerview),
  picasso,
  specs2,
  mockito,
  androidTest)

transitiveAndroidLibs in Android := true

dexMaxHeap in Android := "2048m"

run <<= run in Android

proguardCache in Android := Seq.empty

proguardScala in Android := true

useProguard in Android := true

proguardOptions in Android ++= Settings.proguardCommons

packagingOptions in Android := PackagingOptions(
  excludes = Seq("META-INF/LICENSE.txt", "META-INF/NOTICE.txt", "META-INF/LICENSE", "META-INF/NOTICE")
)