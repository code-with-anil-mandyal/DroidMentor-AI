package com.droidmentorai.domain.useCase

import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.domain.repository.AiChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val repository: AiChatRepository
) {

    operator fun invoke(
        sessionId: Long
    ): Flow<List<ChatMessageEntity>> {

        return repository.getMessagesForSession(sessionId)
    }
}