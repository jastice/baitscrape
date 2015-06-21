package org.gestern.baitscrape

import java.io.{FileOutputStream, PrintWriter, File, FileWriter}

import net.ruippeixotog.scalascraper.browser.Browser

import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

import scala.annotation.tailrec
import scala.io.Source


object Baitscrape extends App {

  val pagecount = args.head.toInt

  val roots = Seq(
    "http://www.upworthy.com/democracy/", "http://www.upworthy.com/diversity-and-equality/",
    "http://www.upworthy.com/economics/", "http://www.upworthy.com/environment/",
    "http://www.upworthy.com/health/", "http://www.upworthy.com/humanity-and-culture/",
    "http://www.upworthy.com/justice/", "http://www.upworthy.com/science-and-technology/"
  )

  def page(root: String, p: Int) = s"$root/page/$p"

  val baitsafe = new File("baitsafe-new")
  baitsafe.createNewFile()

  val writerNew = new PrintWriter(new FileOutputStream(baitsafe))
  val browser = new Browser

  @tailrec def scrapePages(root: String, n: Int, max: Int): Unit = {
    if (n<=max) {
      try {
        val doc = browser get page(root,n)
        for {
          bait <- (doc >> elements("h3")).map(_.text)
          if bait != "Topics" && bait != "Upworthy draws attention to stories that matter."
        } {
          writerNew.println(bait)
          writerNew.flush()
          println(bait)
        }
      } catch {
        case x: Throwable => println(s"Got an error at page $n: ${x.getMessage}")
      }

      Thread.sleep(312)
      scrapePages(root,n+1,max)
    }
  }

  val scrapedBait = for {
    root <- roots
  } scrapePages(root, 1, pagecount)

}
