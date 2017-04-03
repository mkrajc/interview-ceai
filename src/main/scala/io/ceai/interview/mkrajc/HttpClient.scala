package io.ceai.interview.mkrajc

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.{CloseableHttpClient, HttpClientBuilder}
import org.apache.http.util.EntityUtils

import scala.concurrent.Future
import scala.util.Try

trait HttpClient {
  def execute(urlLink: UrlLink): Future[Response]
}

class ApacheHttpClient extends HttpClient {
  val client: CloseableHttpClient = HttpClientBuilder.create().build()
  override def execute(urlLink: UrlLink): Future[Response] = {
    val get = new HttpGet(urlLink.url)
    val tryResp = Try {
      val resp = client.execute(get)
      val body = EntityUtils.toString(resp.getEntity)
      Response(urlLink, resp.getStatusLine.getStatusCode, body)
    }

    Future.fromTry(tryResp)
  }

}

