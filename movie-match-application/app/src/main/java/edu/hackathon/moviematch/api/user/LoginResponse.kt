package edu.hackathon.moviematch.api.user

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("email")
    var email: String,
)