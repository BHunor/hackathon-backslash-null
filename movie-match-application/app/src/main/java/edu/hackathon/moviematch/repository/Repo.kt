package edu.hackathon.moviematch.repository

import edu.hackathon.moviematch.api.film.FilmApi
import edu.hackathon.moviematch.api.film.SearchFilmResponse
import edu.hackathon.moviematch.api.search.AskRequest
import edu.hackathon.moviematch.api.search.AskResponse
import edu.hackathon.moviematch.api.search.SearchApi
import retrofit2.Response

object Repo {

    suspend fun askForFilms(token: String, askRequest: AskRequest): Response<AskResponse>? {
        return SearchApi.getApi()?.askForFilms(token = token, askRequest = askRequest)
    }

    suspend fun searchMovieByName(apiKey: String, language: String, query: String, page: Int): Response<SearchFilmResponse>? {
        return FilmApi.getApi()?.searchMovie(
            apiKey = apiKey,
            language = language,
            query = query,
            page = page,
        )
    }
}