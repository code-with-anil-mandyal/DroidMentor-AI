package com.droidmentorai.data.dto


import com.google.gson.annotations.SerializedName

data class GeminiResponseDto(
    @SerializedName("candidates")
    val candidates: List<Candidate?>?,
    @SerializedName("modelVersion")
    val modelVersion: String?,
    @SerializedName("responseId")
    val responseId: String?,
    @SerializedName("usageMetadata")
    val usageMetadata: UsageMetadata?
)