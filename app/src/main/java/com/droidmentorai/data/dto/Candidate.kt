package com.droidmentorai.data.dto


import com.google.gson.annotations.SerializedName

data class Candidate(
    @SerializedName("content")
    val content: Content?,
    @SerializedName("finishReason")
    val finishReason: String?,
    @SerializedName("index")
    val index: Int?
)