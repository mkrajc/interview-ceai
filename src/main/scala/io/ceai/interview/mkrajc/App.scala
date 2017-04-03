package io.ceai.interview.mkrajc

import java.util.concurrent.Executors

/**
  * Created by martin.krajc on 3. 4. 2017.
  */
object App {
  def main(args: Array[String]): Unit = {
    val exec = Executors.newFixedThreadPool(3)
    val client = new ApacheHttpClient
    val parser = new JsoupParser
    val disp = new ExecutorDispatcher(exec)
    val crawler = new Crawler(client, parser, disp)

    disp.handle(crawler, UrlLink("http://google.sk"))

  }
}
