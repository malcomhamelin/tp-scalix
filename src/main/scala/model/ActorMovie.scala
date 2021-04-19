package model

case class ActorMovie(character: String,
                      creditId: String,
                      voteCount: Int,
                      video: Boolean,
                      adult: Boolean,
                      voteAverage: Double,
                      title: String,
                      genreIds: List[Int],
                      originalLanguage: String,
                      originalTitle: String,
                      popularity: Double,
                      id: Int,
                      backdropPath: String,
                      overview: String,
                      posterPath: String,
                     )
