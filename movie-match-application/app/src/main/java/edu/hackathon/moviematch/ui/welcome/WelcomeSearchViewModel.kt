package edu.hackathon.moviematch.ui.welcome

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.hackathon.moviematch.api.film.FilmDetails
import edu.hackathon.moviematch.api.film.SearchFilmResponse
import edu.hackathon.moviematch.api.film.SearchFilmResultResponse
import edu.hackathon.moviematch.api.search.AskRequest
import edu.hackathon.moviematch.api.search.AskResponse
import edu.hackathon.moviematch.api.search.Message
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.ApiResults
import edu.hackathon.moviematch.ui.MovieMatchViewModel
import kotlinx.coroutines.launch


@Suppress("UNCHECKED_CAST")
class WelcomeSearchViewModelFactory(
    private val prefs: SharedPreferences,
    private val repo: Repo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WelcomeSearchViewModel(_prefs = prefs, _repo = repo) as T
    }
}
class WelcomeSearchViewModel(
    _prefs: SharedPreferences,
    _repo: Repo
) : MovieMatchViewModel (
    _prefs = _prefs,
    _repo = _repo,
) {
    companion object {
        val TAG: String = WelcomeSearchFragment::class.java.simpleName
    }

    var backFromDetails: Boolean = false;
    var searchWord: String = "";

    private var _askResult = MutableLiveData<ApiResults>()
    val askResult get() = _askResult
    private var _askResponse: AskResponse? = null
    val askResponse get() = _askResponse

    private var _searchResult = MutableLiveData<ApiResults>()
    val searchResult get() = _searchResult

    private var _detailResult = MutableLiveData<ApiResults>()
    val detailResult get() = _detailResult
    private var _searchResponse: SearchFilmResponse? = null
    val searchResponse get() = _searchResponse

    private var _movieDetailResponse: FilmDetails? = null
    val movieDetailsResponse get() = _movieDetailResponse

    private var _loadFilmsResult = MutableLiveData<ApiResults>()
    val loadFilmsResult get() = _loadFilmsResult
    private var _loadFilmsResponse: MutableList<SearchFilmResponse>? = null
    val loadFilmsResponse get() = _loadFilmsResponse

    private var _lastAskedContents: MutableList<String>? = null
    val lastAskedContents get() = _lastAskedContents

    var selectedItem: SearchFilmResultResponse? = null
    fun askForFilms(content: String) {
        _askResult.value = ApiResults.LOADING

        val askContent = "Suggest films, which are connected to: `$content`. Provide only the titles, separate it with semicolon, without any extra signs."
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

                    _lastAskedContents = mutableListOf()

                    askResponse!!.choices[0].message.content.replace(".", "")
                        .split("; ").
                        forEach{
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

    fun searchForFilm(query: String)  {
        _searchResult.value = ApiResults.LOADING

        viewModelScope.launch {
            try {
                val  response = _repo.searchMovieByName(
                    apiKey = apiKey,
                    language = "en-US",
                    query = query,
                    page = 1
                )

                if (response?.isSuccessful == true) {
                    _searchResponse = response.body()!!

                    _searchResult.value = ApiResults.SUCCESS

                    Log.d(TAG, _searchResponse.toString())
                }
                else {
                    _searchResult.value = ApiResults.INVALID_TOKEN
                }
            }
            catch (ex: Exception) {
                Log.e(TAG, ex.message, ex)
                _searchResult.value = ApiResults.UNKNOWN_ERROR
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

                        Log.d(TAG, "kicsikutya")

                        _loadFilmsResponse!!.add(
                            element = response.body()!!
                        )

                        _loadFilmsResult.value = ApiResults.SUCCESS

                        Log.d(TAG, _searchResponse.toString())
                    }
                    else {
                        _loadFilmsResult.value = ApiResults.INVALID_TOKEN
                    }
                }
                catch (ex: Exception) {
                    Log.e(TAG, ex.message, ex)
                    _searchResult.value = ApiResults.UNKNOWN_ERROR
                }
            }
        }

    }

    fun detailMovie(id:Int)  {
        _detailResult.value = ApiResults.LOADING

        viewModelScope.launch {
            try {
                val  response = _repo.getMovieDetail(
                    id = id,
                    apiKey = apiKey,
                    language = "en-US",
                )

                if (response?.isSuccessful == true) {
                    _movieDetailResponse = response.body()!!

                    _detailResult.value = ApiResults.SUCCESS

                    Log.d(TAG, _searchResponse.toString())
                }
                else {
                    _detailResult.value = ApiResults.INVALID_TOKEN
                }
            }
            catch (ex: Exception) {
                Log.e(TAG, ex.message, ex)
                _detailResult.value = ApiResults.UNKNOWN_ERROR
            }
        }
    }


}