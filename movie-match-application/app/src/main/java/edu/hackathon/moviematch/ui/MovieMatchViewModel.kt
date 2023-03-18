package edu.hackathon.moviematch.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
abstract class MovieMatchViewModel(
    protected val _prefs: SharedPreferences,
) : ViewModel() {

    companion object {
        val TAG: String = MovieMatchViewModel::class.java.simpleName
    }


}