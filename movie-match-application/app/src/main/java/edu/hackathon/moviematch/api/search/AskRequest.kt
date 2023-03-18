package edu.hackathon.moviematch.api.search

import com.google.gson.annotations.SerializedName

data class AskRequest(
    @SerializedName("model")
    var model: String = "gpt-3.5-turbo",
    @SerializedName("messages")
    var messages: List<Message> = listOf(Message()),
    @SerializedName("temperature")
    var temperature: Float = 0.7f
)

data class Message(
    @SerializedName("role")
    var role: String = "user",
    @SerializedName("content")
    var content: String = "Tell"
)