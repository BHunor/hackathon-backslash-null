package edu.hackathon.moviematch.ui.authentication

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.hackathon.moviematch.api.user.LoginRequest
import edu.hackathon.moviematch.api.user.LoginResponse
import edu.hackathon.moviematch.api.user.LoginResults
import edu.hackathon.moviematch.repository.Repo
import edu.hackathon.moviematch.ui.MovieMatchViewModel
import edu.hackathon.moviematch.ui.welcome.WelcomeSearchViewModel
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class AuthenticationViewModelFactory(
    private val prefs: SharedPreferences,
    private val repo: Repo
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthenticationViewModel(_prefs = prefs, _repo = repo) as T
    }
}

class AuthenticationViewModel(
    _prefs: SharedPreferences,
    _repo: Repo
) : MovieMatchViewModel(
    _prefs = _prefs,
    _repo = _repo,
) {
    companion object{
        val TAG: String = AuthenticationViewModel::class.java.simpleName
    }

    val loginResult: MutableLiveData<LoginResults> = MutableLiveData()


    fun login(email: String, password: String) {
        loginResult.value = LoginResults.LOADING
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
           loginResult.value = LoginResults.INVALID_CREDENTIALS
            return
        }
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
//                val loginRequest = LoginRequest("lazar.zsolt@student.ms.sapientia.ro", "3tracker_student19")
                val response = Repo.loginUser(loginRequest = loginRequest)
                if (true){//response?.isSuccessful == true) {
                    //Log.d(TAG, "Login response ${response.body()}")
//                    MyApplication.token = response.body()!!.token
//                    MyApplication.deadline = response.body()!!.deadline
                    loginResult.value = LoginResults.SUCCESS
                } else {
                    //Log.d(TAG, "Login error response ${response?.errorBody()}")
                    loginResult.value = LoginResults.INVALID_CREDENTIALS
                }

            } catch (ex: Exception) {
                Log.e(TAG, ex.message, ex)
                loginResult.value = LoginResults.UNKNOWN_ERROR
            }
        }
    }
}