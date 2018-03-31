import mill._
import mill.scalalib._
import coursier.maven.MavenRepository
object milltest extends ScalaModule {
  /** Name of project */
  def name = "brotliexec"

  /** Organization */
  def organization = "com.github.sguzman"

  /** Project version */
  def version = "1.0"

  /** Scala version */
  def scalaVersion = "2.12.4"

  /** Define Main */
  def mainClass = Some("com.github.sguzman.scraper.stream.lord.Main")

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

  /** Ivy dependencies */
  def ivyDeps = Agg(
    ivy"com.outr::scribe:2.3.1"
  )

  /** Scala compiler plugins */
  def scalacPluginIvyDeps = Agg(
    ivy"org.scoverage::sbt-scoverage:1.6.0-M2"
  )

  def forkArgs = Seq("-Xmx4g")
}