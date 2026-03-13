package com.droidmentorai.data.mapper

import com.droidmentorai.core.utils.ROLE_ASSISTANT
import com.droidmentorai.data.dto.GeminiResponseDto
import com.droidmentorai.domain.model.AIChatMessages

object GeminiChatMapper {
    fun mapToDomain(
        response: GeminiResponseDto
    ): AIChatMessages{
        val text = response
            .candidates
            ?.firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            ?: "No response"
        return AIChatMessages(
            message = text,
            role = ROLE_ASSISTANT
        )
    }
}