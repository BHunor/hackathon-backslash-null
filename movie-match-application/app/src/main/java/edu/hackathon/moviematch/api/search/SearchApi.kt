package edu.hackathon.moviematch.api.search

import edu.hackathon.moviematch.api.OpenAiClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SearchApi {

    companion object {
        fun getApi(): SearchApi? {
            return OpenAiClient.client?.create(SearchApi::class.java)
        }
    }

    @POST("/completions")
    suspend fun askForFilms(@Header("Authorization") token: String, @Body askRequest: AskRequest): Response<AskResponse>
}