package com.droidmentorai.domain.useCase

import com.droidmentorai.domain.repository.AiChatRepository
import javax.inject.Inject

class UpdateLastMessageUseCase @Inject constructor(
    private val repository: AiChatRepository
) {

    suspend operator fun invoke(
        sessionId: Long,
        message: String
    ) {

        repository.updateLastMessage(
            sessionId,
            message
        )
    }
}