package edu.hackathon.moviematch.api.film

import edu.hackathon.moviematch.api.MovieDBClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {

    companion object {
        fun getApi(): FilmApi? {
            return MovieDBClient.client?.create(FilmApi::class.java)
        }
    }
    @GET("/3/search/movie")
    suspend fun searchMovie(@Query("api_key") apiKey: String,
                            @Query("language") language: String,
                            @Query("query") query: String,
                            @Query("page") page: Int
                            ): Response<SearchFilmResponse>

    @GET("/3/movie/{id}")
    suspend fun getMovieDetail(@Path("id") id:Int,
                               @Query("api_key") apiKey: String,
                               @Query("language") language: String): Response<FilmDetails>
//movie/281957/similar?api_key=55b7f8db758832ecfab9176b81841cdf&language=en-US&page=1
    @GET("/3/movie/{id}/similar")
    suspend fun getSimilarMovie(@Path("id") id:Int,
                               @Query("api_key") apiKey: String,
                               @Query("language") language: String): Response<SearchFilmResponse>
}