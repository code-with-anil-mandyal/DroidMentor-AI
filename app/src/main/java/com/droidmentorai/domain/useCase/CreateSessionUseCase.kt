package com.droidmentorai.domain.useCase

import com.droidmentorai.domain.repository.AiChatRepository
import javax.inject.Inject

class CreateSessionUseCase @Inject constructor(
    private val repository: AiChatRepository
) {

    suspend operator fun invoke(
        title: String
    ): Long {

        return repository.createSession(title)
    }
}