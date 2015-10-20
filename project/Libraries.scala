import sbt._

object Libraries {

  def onCompile(dep: ModuleID): ModuleID = dep % "compile"
  def onTest(dep: ModuleID): ModuleID = dep % "test"

  object scala {

    lazy val scalaReflect = "org.scala-lang" % "scala-reflect" % Versions.scalaV
    lazy val scalap = "org.scala-lang" % "scalap" % Versions.scalaV
  }

  object android {

    def androidDep(module: String) = "com.android.support" % module % Versions.androidV

    lazy val androidRecyclerview = androidDep("recyclerview-v7")
    lazy val androidCardView = androidDep("cardview-v7")
    lazy val androidDesign = androidDep("design")
  }

  object graphics {
    lazy val picasso = "com.squareup.picasso" % "picasso" % Versions.picassoV
  }

  object macroid {

    def macroid(module: String = "") =
      "org.macroid" %% s"macroid${if(!module.isEmpty) s"-$module" else ""}" % Versions.macroidV

    lazy val macroidRoot = macroid()
  }

  object test {
    lazy val specs2 = "org.specs2" %% "specs2-core" % Versions.specs2V % "test"
    lazy val androidTest = "com.google.android" % "android" % "4.1.1.4" % "test"
    lazy val mockito = "org.specs2" % "specs2-mock_2.11" % Versions.mockitoV % "test"
  }
}