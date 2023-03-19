package edu.hackathon.moviematch.ui.feed

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.hackathon.moviematch.api.film.SearchFilmResponse
import edu.hackathon.moviematch.api.film.SearchFilmResultResponse
import edu.hackathon.moviematch.api.search.AskRequest
import edu.hackathon.moviematch.api.search.AskResponse
import edu.hackathon.moviematch.api.search.Message
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.MovieMatchViewModel
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModel
import kotlinx.coroutines.launch

class FeedViewModelFactory(
    private val prefs: SharedPreferences,
    private val repo: Repo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FeedViewModel(_prefs = prefs, _repo = Repo) as T
    }
}
class FeedViewModel(
    _prefs: SharedPreferences,
    _repo: Repo
) : MovieMatchViewModel(
    _prefs = _prefs,
    _repo = _repo
) {
    companion object {
        val TAG: String = FeedViewModel::class.java.simpleName
    }

    private var _askResult = MutableLiveData<ApiResults>()
    val askResult get() = _askResult
    private var _askResponse: AskResponse? = null
    val askResponse get() = _askResponse

    private var _lastAskedContents: MutableList<String>? = null
    val lastAskedContents get() = _lastAskedContents

    private var _loadFilmsResult = MutableLiveData<ApiResults>()
    val loadFilmsResult get() = _loadFilmsResult
    private var _loadFilmsResponse: MutableList<SearchFilmResponse>? = null
    val loadFilmsResponse get() = _loadFilmsResponse

    private var _similarMoviesResult = MutableLiveData<ApiResults>()
    val similarMoviesResult get() = _similarMoviesResult
    private var _similarMoviesRespone: SearchFilmResponse? = null
    val similarMoviesRespone get() = _similarMoviesRespone

    var selectedItem: SearchFilmResultResponse? = null

    fun askForFilms() {
        _askResult.value = ApiResults.LOADING

        val askContent = "Like The Lord Of The Rings Trilogy, Peaky Blinders, Edge of Tomorrow." +
                "Provide only the titles, separate it with semicolon, without any extra signs."

        viewModelScope.launch {
            try {
                val response = _repo.askForFilms(
                    token = "Bearer $token",
                    askRequest = AskRequest(
                        messages = listOf(
                            Message(content = askContent)
                        )
                    )
                )

                if (response?.isSuccessful == true) {
                    _askResponse = response.body()!!
                    Log.d("XXX","itt")
                    Log.d("XXX", _askResponse.toString())
                    _lastAskedContents = mutableListOf()

                    askResponse!!.choices[0].message.content.replace(".", "")
                        .split("; ")
                        .forEach {
                            lastAskedContents!!.add(it.replace("; ", ""))
                        }
                    _askResult.value = ApiResults.SUCCESS
                }
                else {
                    _askResult.value = ApiResults.INVALID_TOKEN
                }
            }
            catch (ex: Exception) {
                Log.e(TAG, ex.message, ex)
                _askResult.value = ApiResults.UNKNOWN_ERROR
            }
        }
    }

    fun searchForFilms(queries: List<String>) {
        _loadFilmsResult.value = ApiResults.LOADING

        _loadFilmsResponse = mutableListOf()

        for (query in queries) {

            viewModelScope.launch {
                try {
                    val  response = _repo.searchMovieByName(
                        apiKey = apiKey,
                        language = "en-US",
                        query = query,
                        page = 1
                    )

                    if (response?.isSuccessful == true) {
                        _loadFilmsResponse!!.add(
                            element = response.body()!!
                        )
                        _loadFilmsResult.value = ApiResults.SUCCESS
                        Log.d(WelcomeSearchViewModel.TAG, _loadFilmsResponse.toString())
                    }
                    else {
                        _loadFilmsResult.value = ApiResults.INVALID_TOKEN
                    }
                }
                catch (ex: Exception) {
                    Log.e(WelcomeSearchViewModel.TAG, ex.message, ex)
                    _loadFilmsResult.value = ApiResults.UNKNOWN_ERROR
                }
            }
        }
    }

    fun similarMovies()  {
        _similarMoviesResult.value = ApiResults.LOADING
        val array = mutableListOf<Int>();
        array.add(281957);
        array.add(137113)
        for (film in array) {
            viewModelScope.launch {
                try {
                    val response = _repo.getSimilarMovie(
                        id = film,
                        apiKey = apiKey,
                        language = "en-US",
                    )

                    if (response?.isSuccessful == true) {
                        _similarMoviesRespone
                        _similarMoviesRespone = response.body()!!
                        _similarMoviesResult.value = ApiResults.SUCCESS
//                    Log.d(WelcomeSearchViewModel.TAG, _searchResponse.toString())
                    } else {
                        _similarMoviesResult.value = ApiResults.INVALID_TOKEN
                    }
                } catch (ex: Exception) {
                    Log.e(WelcomeSearchViewModel.TAG, ex.message, ex)
                    _similarMoviesResult.value = ApiResults.UNKNOWN_ERROR
                }
            }
        }
    }
}