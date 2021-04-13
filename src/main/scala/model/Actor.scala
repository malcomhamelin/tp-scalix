package model

case class Actor(profilePath: Option[String],
                 adult: Boolean,
                 id: Int,
                 name: String,
                 popularity: Double
                )