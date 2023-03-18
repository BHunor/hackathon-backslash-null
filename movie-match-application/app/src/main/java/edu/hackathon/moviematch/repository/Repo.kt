package edu.hackathon.moviematch.repository

import edu.hackathon.moviematch.api.search.AskRequest
import edu.hackathon.moviematch.api.search.AskResponse
import edu.hackathon.moviematch.api.search.SearchApi
import retrofit2.Response

object Repo {

    suspend fun askForFilms(token: String, askRequest: AskRequest): Response<AskResponse>? {
        return SearchApi.getApi()?.askForFilms(token = token, askRequest = askRequest)
    }
}