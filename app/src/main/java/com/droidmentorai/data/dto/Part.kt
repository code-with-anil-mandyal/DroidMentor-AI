package com.droidmentorai.data.dto


import com.google.gson.annotations.SerializedName

data class Part(
    @SerializedName("text")
    val text: String?
)