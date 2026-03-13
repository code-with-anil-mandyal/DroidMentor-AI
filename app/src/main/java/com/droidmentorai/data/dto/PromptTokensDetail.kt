package com.droidmentorai.data.dto


import com.google.gson.annotations.SerializedName

data class PromptTokensDetail(
    @SerializedName("modality")
    val modality: String?,
    @SerializedName("tokenCount")
    val tokenCount: Int?
)