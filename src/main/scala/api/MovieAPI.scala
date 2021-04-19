package api

import org.json4s.{DefaultFormats, Formats}
import org.json4s.native.JsonMethods.parse
import model.{Actor, FindActorMoviesResponse, FindMovieDirectorResponse, FullName, PaginatedResponse}

import java.net.URLEncoder
import scala.collection.Set
import scala.io.Source

object MovieAPI extends App {
  implicit val formats: Formats = DefaultFormats

  private def buildUrl(route: String, query: String): String = {
    s"https://api.themoviedb.org/3$route?api_key=2365f8c762228458874eeb1836406ae4&query=$query"
  }

  def findActorId(name: String, surname: String): Option[Int] = {
    val query =  URLEncoder.encode(s"$name $surname", "UTF-8")
    val response = Source.fromURL(buildUrl("/search/person", query))
    val paginatedResponse = parse(response.mkString).camelizeKeys.extract[PaginatedResponse[Actor]]

    paginatedResponse.results.headOption.map(_.id)
  }

  def findActorMovies(id: Int): Set[(Int, String)] = {
    val response = Source.fromURL(buildUrl(s"/person/$id/movie_credits", ""))
    val moviesResponse = parse(response.mkString).camelizeKeys.extract[FindActorMoviesResponse]

    moviesResponse.cast.map(movie => (movie.id, movie.originalTitle)).toSet
  }

  def findMovieDirector(id: Int): Option[(Int, String)] = {
    val response = Source.fromURL(buildUrl(s"/movie/$id/credits", ""))
    val movieResponse = parse(response.mkString).camelizeKeys.extract[FindMovieDirectorResponse]
    val director = movieResponse.crew.find(_.job == "Director")

    Option.apply(director.map(_.id).get, director.map(_.name).get)
  }

}
