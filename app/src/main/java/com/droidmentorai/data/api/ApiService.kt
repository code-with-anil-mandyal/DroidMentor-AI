package com.droidmentorai.data.api


import com.droidmentorai.data.api.ApiRoutes.AI_CHAT
import com.droidmentorai.data.dto.GeminiResponseDto
import com.droidmentorai.data.request.GeminiRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST(AI_CHAT)
    suspend fun generateContent(
        @Query("key") apiKey : String,
        @Body request : GeminiRequest
    ) : GeminiResponseDto
}