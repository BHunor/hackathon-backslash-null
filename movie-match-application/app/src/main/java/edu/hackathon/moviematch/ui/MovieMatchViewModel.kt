package edu.hackathon.moviematch.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import edu.hackathon.moviematch.api.Constants
import edu.hackathon.moviematch.repository.Repo
abstract class MovieMatchViewModel(
    protected val _prefs: SharedPreferences,
    protected val _repo: Repo
) : ViewModel() {

    companion object {
        val TAG: String = MovieMatchViewModel::class.java.simpleName
    }

    protected var _token: String? = Constants.TOKEN
    protected val token get() = _token!!

}