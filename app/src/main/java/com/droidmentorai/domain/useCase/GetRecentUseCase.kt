package com.droidmentorai.domain.useCase

import com.droidmentorai.data.room.entity.ChatSessionEntity
import com.droidmentorai.domain.repository.AiChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentUseCase @Inject constructor(
    private val repository: AiChatRepository
) {
    operator fun invoke() : Flow<List<ChatSessionEntity>>{
        return repository.getRecentSessions()
    }
}