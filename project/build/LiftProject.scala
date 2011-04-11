import sbt._

class LiftProject(info: ProjectInfo) extends DefaultWebProject(info) {
  val liftVersion = "2.3"

  // uncomment the following if you want to use the snapshot repo
  // val scalatoolsSnapshot = ScalaToolsSnapshots

  // If you're using JRebel for Lift development, uncomment
  // this line
  // override def scanDirectories = Nil

  override def libraryDependencies = Set(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default" withSources,
    "net.liftweb" %% "lift-record" % liftVersion % "compile->default" withSources,
    "net.liftweb" %% "lift-squeryl-record" % liftVersion % "compile->default" withSources,
    "org.squeryl" % "squeryl_2.8.1" % "0.9.4-RC6" % "compile->default" withSources,
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "test->default",
    "junit" % "junit" % "4.5" % "test->default",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "org.scala-tools.testing" %% "specs" % "1.6.6" % "test->default",
//    "net.liftweb" %% "lift-mapper" % liftVersion,
    "mysql" % "mysql-connector-java" % "5.1.9"
  ) ++ super.libraryDependencies
}
