package api

import org.json4s.{DefaultFormats, Formats}
import org.json4s.native.JsonMethods.parse

import model.{Actor, FullName, PaginatedResponse}
import java.net.URLEncoder
import scala.io.Source

object MovieAPI extends App {
  implicit val formats: Formats = DefaultFormats

  findActorId("Jason", "Statham") match {
    case Some(value) => println(value)
    case None => println("erreur")
  }

  private def buildUrl(route: String, query: String): String = {
    s"https://api.themoviedb.org/3$route?api_key=2365f8c762228458874eeb1836406ae4&query=$query"
  }

  def findActorId(name: String, surname: String): Option[Int] = {
    val query =  URLEncoder.encode(s"$name $surname", "UTF-8")
    val response = Source.fromURL(buildUrl("/search/person", query))
    val paginatedResponse = parse(response.mkString).camelizeKeys.extract[PaginatedResponse[Actor]]
    paginatedResponse.results.headOption.map(_.id)
  }

  def findActorMovies(id: Int): Set[(Int, String)] = ???

  def findMovieDirector(id: Int): Option[(Int, String)] = ???

  def request(actor1: FullName, actor2: FullName): Set[(String, String)] = ???
}
