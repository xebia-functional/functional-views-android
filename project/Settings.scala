import sbt._

object Settings {

  lazy val resolvers =
    Seq(
        Resolver.mavenLocal,
        DefaultMavenRepository,
        "jcenter" at "http://jcenter.bintray.com",
        "47 Degrees Bintray Repo" at "http://dl.bintray.com/47deg/maven",
        Resolver.typesafeRepo("releases"),
        Resolver.typesafeRepo("snapshots"),
        Resolver.typesafeIvyRepo("snapshots"),
        Resolver.sonatypeRepo("releases"),
        Resolver.sonatypeRepo("snapshots"),
        Resolver.defaultLocal,
        "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
      )

  lazy val proguardCommons = Seq(
    "-ignorewarnings",
    "-keep class scala.Dynamic",
    "-keep class com.fortysevendeg.android.functionalview.** { *; }",
    "-keep class macroid.** { *; }",
    "-keep class android.** { *; }")

}
