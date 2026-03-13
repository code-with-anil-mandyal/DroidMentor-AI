package com.droidmentorai.data.dto


import com.google.gson.annotations.SerializedName

data class UsageMetadata(
    @SerializedName("candidatesTokenCount")
    val candidatesTokenCount: Int?,
    @SerializedName("promptTokenCount")
    val promptTokenCount: Int?,
    @SerializedName("promptTokensDetails")
    val promptTokensDetails: List<PromptTokensDetail?>?,
    @SerializedName("thoughtsTokenCount")
    val thoughtsTokenCount: Int?,
    @SerializedName("totalTokenCount")
    val totalTokenCount: Int?
)