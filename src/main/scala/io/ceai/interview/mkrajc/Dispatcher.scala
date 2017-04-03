package io.ceai.interview.mkrajc

import java.util.concurrent.ExecutorService

import akka.actor.ActorRef


trait Dispatcher {
  def handle(crawler: Crawler, urlLink: UrlLink): Unit
  def handleError(crawler: Crawler, urlLink: UrlLink, error: Throwable): Unit
}

class ExecutorDispatcher(exec: ExecutorService) extends Dispatcher {

  override def handle(crawler: Crawler, urlLink: UrlLink): Unit = {
    exec.submit(new Runnable {
      override def run() = {
        println(s"Starting crawl $urlLink")
        crawler.crawl(urlLink)
      }
    })
  }

  override def handleError(crawler: Crawler, urlLink: UrlLink, error: Throwable): Unit = {
    println(s"error for link: $urlLink")
    error.printStackTrace()
  }
}

case class CrawlMsg(urlLink: UrlLink)
case class HandleError(urlLink: UrlLink, error: Throwable)

class AkkaDispatcher(actorRef: ActorRef) extends Dispatcher {
  override def handle(crawler: Crawler, urlLink: UrlLink): Unit = actorRef ! CrawlMsg(urlLink)
  override def handleError(crawler: Crawler, urlLink: UrlLink, error: Throwable): Unit = actorRef ! HandleError(urlLink, error)
}
