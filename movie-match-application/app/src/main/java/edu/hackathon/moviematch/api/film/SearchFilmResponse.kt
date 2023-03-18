package edu.hackathon.moviematch.api.film

import com.google.gson.annotations.SerializedName

data class SearchFilmResponse(
    @SerializedName("id")
    var movieId: Int,
    @SerializedName("original_title")
    var original_title: String,
    @SerializedName("overview")
    var overview: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("popularity")
    var popularity: Float,
    @SerializedName("title")
    var title: String,
    @SerializedName("video")
    var video: Boolean,
    @SerializedName("vote_average")
    var rating: Float
)