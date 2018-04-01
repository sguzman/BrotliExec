import mill._
import mill.scalalib._
import coursier.maven.MavenRepository
import publish._
import java.io.File

object brotli extends PublishModule {
  /** Publish version */
  def publishVersion = "1.0.0"

  /** Define maven POM for publishing */
  def pomSettings = PomSettings(
    description = "Exec wrapper to local brotli executable for compression and decompression",
    organization = "com.github.sguzman",
    url = "https://github.com/sguzman/BrotliExec",
    licenses = Seq(License.Unlicense),
    versionControl = VersionControl.github("sguzman", "BrotliExec"),
    developers = Seq(
      Developer("sguzman", "Salvador Guzman","https://github.com/sguzman")
    )
  )

  /** Custom task to clean out/brotli */
  def clean() = T.command{
    def delete(file: File): Unit = {
      if (file.exists)
        if (file.isDirectory)
          Option(file.listFiles).map(_.toList).getOrElse(Nil).foreach(delete)
        else file.delete()

    }

    delete(new File("./out/brotli"))
  }

  /** Name of project */
  def name = "brotliexec"

  /** Organization */
  def organization = "com.github.sguzman"

  /** Project version */
  def version = "1.0"

  /** Scala version */
  def scalaVersion = "2.12.4"

  /** Scalac parameters */
  def scalacOptions = Seq("-Ydelambdafy:inline", "-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

  /** Javac parameters */
  def javacOptions = Seq("-server")

  /** Resolvers */
  def repositories = super.repositories ++ Seq(
    MavenRepository("https://repo1.maven.org/maven2"),
    MavenRepository("https://oss.sonatype.org/content/repositories/public"),
    MavenRepository("https://repo.typesafe.com/typesafe/releases"),
    MavenRepository("https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases"),
    MavenRepository("https://oss.sonatype.org/content/repositories/releases"),
    MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"),
    MavenRepository("https://jcenter.bintray.com")
  )

  /** Test suite */
  object test extends Tests{
    /** Test Framework */
    def testFrameworks = Seq("org.scalatest.tools.Framework")
    /** Ivy dependencies */
    def ivyDeps = Agg(ivy"org.scalatest::scalatest:3.0.4")
  }

  /** Ivy dependencies */
  //def ivyDeps = Agg()

  /** Scala compiler plugins */
  //def scalacPluginIvyDeps = Agg()

  def forkArgs = Seq("-Xmx4g")
}