package edu.hackathon.moviematch.api.user

import edu.hackathon.moviematch.api.ApiClient
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {

    companion object {
        fun getApi(): UserApi? {
            return ApiClient.client?.create(UserApi::class.java)
        }
    }

    @POST("/user/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

//    @GET("/user")
//    suspend fun getMyUser(@Header("token") token: String): Response<UserResponse>

}