package edu.hackathon.moviematch.ui.welcome

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.hackathon.moviematch.api.search.AskRequest
import edu.hackathon.moviematch.api.search.AskResponse
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

    private var _askResult = MutableLiveData<ApiResults>()
    val askResult get() = _askResult
    private var _askResponse: AskResponse? = null
    val askResponse get() = _askResponse

    fun askForFilms(content: String) {
        _askResult.value = ApiResults.LOADING
        viewModelScope.launch {
            try {
                val response = _repo.askForFilms(
                    token = token,
                    askRequest = AskRequest(

                    )
                )

                if (response?.isSuccessful == true) {
                    _askResponse = response.body()!!

                    _askResult.value = ApiResults.SUCCESS

                    Log.d(TAG, _askResponse.toString())
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

}