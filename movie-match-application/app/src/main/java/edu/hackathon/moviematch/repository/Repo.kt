package edu.hackathon.moviematch.repository

import edu.hackathon.moviematch.api.film.FilmApi
import edu.hackathon.moviematch.api.film.FilmDetails
import edu.hackathon.moviematch.api.film.SearchFilmResponse
import edu.hackathon.moviematch.api.search.AskRequest
import edu.hackathon.moviematch.api.search.AskResponse
import edu.hackathon.moviematch.api.search.SearchApi
import edu.hackathon.moviematch.api.user.LoginRequest
import edu.hackathon.moviematch.api.user.LoginResponse
import edu.hackathon.moviematch.api.user.UserApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    suspend fun getMovieDetail(id: Int, apiKey: String, language: String): Response<FilmDetails>? {
        return FilmApi.getApi()?.getMovieDetail(
            id = id,
            apiKey = apiKey,
            language = language,
        )
    }

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>? {
        return UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }

}