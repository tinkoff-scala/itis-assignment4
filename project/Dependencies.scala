import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.8"
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.15.0"
  lazy val scalaTestPlus = "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0"
}
