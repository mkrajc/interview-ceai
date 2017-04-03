package io.ceai.interview.mkrajc

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element

trait HttpParser {
  def parse(response: Response): List[UrlLink]
}

class JsoupParser extends HttpParser {
  import net.ruippeixotog.scalascraper.dsl.DSL._
  import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
  import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

  val browser = JsoupBrowser()
  override def parse(response: Response): List[UrlLink] = {
    val doc = browser.parseString(response.response)
    val linksElems: List[Element] = doc >> elementList("a")
    linksElems.map(_.attr("href")).map(UrlLink)
  }
}
