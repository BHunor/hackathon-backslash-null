package edu.hackathon.moviematch.api.search

import com.google.gson.annotations.SerializedName

data class AskResponse(
    @SerializedName("id")
    var id: String,
    @SerializedName("created")
    var created: String,
    @SerializedName("choices")
    var choices: List<Choice>
)

data class Choice(
    @SerializedName("message")
    var message: Message
)