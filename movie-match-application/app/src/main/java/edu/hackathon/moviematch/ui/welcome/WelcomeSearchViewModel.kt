package edu.hackathon.moviematch.ui.welcome

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.hackathon.moviematch.ui.MovieMatchViewModel


@Suppress("UNCHECKED_CAST")
class WelcomeSearchViewModelFactory(
    private val prefs: SharedPreferences,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WelcomeSearchViewModel(_prefs = prefs) as T
    }
}
class WelcomeSearchViewModel(
    _prefs: SharedPreferences
) : MovieMatchViewModel (
    _prefs = _prefs
) {
    companion object {
        val TAG: String = WelcomeSearchFragment::class.java.simpleName
    }

}