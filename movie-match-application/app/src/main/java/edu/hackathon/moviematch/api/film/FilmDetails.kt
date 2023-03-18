package edu.hackathon.moviematch.api.film

import com.google.gson.annotations.SerializedName

data class FilmDetails(
    @SerializedName("id")
    var movieId: Int,
    @SerializedName("backdrop_path")
    var backdrop_path: String,
    @SerializedName("original_title")
    var original_title: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("genres")
    var genres: List<Genre>
)

data class Genre(
    @SerializedName("id")
    var genreId: Int,
    @SerializedName("name")
    var name: String
)