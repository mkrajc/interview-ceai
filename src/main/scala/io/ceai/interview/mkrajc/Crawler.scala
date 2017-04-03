package io.ceai.interview.mkrajc

import akka.actor.Actor

import scala.util.{Failure, Success}

class Crawler(client: HttpClient, parser: HttpParser, disp: Dispatcher) {
  import scala.concurrent.ExecutionContext.Implicits.global
  def crawl(urlLink: UrlLink): Unit = {
    println(s"[${Thread.currentThread().getName}] Start: $urlLink")
    val f = client.execute(urlLink)
    f.onComplete {
      case Success(resp) => {
        println(s"[${Thread.currentThread().getName}] Done: $urlLink")
        parser.parse(resp).foreach(url => disp.handle(this, url))
      }
      case Failure(err) => {
        println(s"[${Thread.currentThread().getName}] Error: $urlLink $err")
        disp.handleError(this, urlLink, err)
      }
    }
  }
}
// just playground how to extend to actor model
// interview stopped aroung this point
class AkkaCrawler(dispatcher: Dispatcher) extends Actor{
  val client = new ApacheHttpClient
  val parser = new JsoupParser
  // val crawler = new Crawler()
  // TODO ....
  override def receive: Receive = {
    case CrawlMsg(url) =>
  }
}
