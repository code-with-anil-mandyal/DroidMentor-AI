package com.droidmentorai.domain.useCase

import com.droidmentorai.domain.model.AIChatMessages
import com.droidmentorai.domain.repository.AiChatRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: AiChatRepository
) {

    suspend operator fun invoke(
        prompt: String,
        apiKey: String
    ): AIChatMessages {

        return repository.sendAiChatMessage(
            message = prompt,
            apiKey = apiKey
        )
    }
}