name := "baitscrape"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.6"


libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "0.1.1"

initialCommands in console :=
"""
  |import net.ruippeixotog.scalascraper.browser.Browser
  |
  |import net.ruippeixotog.scalascraper.dsl.DSL._
  |import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
  |import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
  |
  |val browser = new Browser
  |
""".stripMargin