package com.droidmentorai.data.dto


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("parts")
    val parts: List<Part?>?,
    @SerializedName("role")
    val role: String?
)