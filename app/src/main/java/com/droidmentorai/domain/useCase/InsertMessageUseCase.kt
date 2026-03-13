package com.droidmentorai.domain.useCase

import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.domain.repository.AiChatRepository
import javax.inject.Inject

class InsertMessageUseCase @Inject constructor(
    private val repository: AiChatRepository
) {

    suspend operator fun invoke(
        message: ChatMessageEntity
    ) {
        repository.insertMessage(message)
    }
}