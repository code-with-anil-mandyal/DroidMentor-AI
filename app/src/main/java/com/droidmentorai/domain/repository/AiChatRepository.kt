package com.droidmentorai.domain.repository

import com.droidmentorai.core.utils.ROLE_ASSISTANT
import com.droidmentorai.core.utils.ROLE_USER
import com.droidmentorai.data.request.Content
import com.droidmentorai.data.request.Part
import com.droidmentorai.data.room.dao.ChatSessionDao
import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.data.room.entity.ChatSessionEntity
import com.droidmentorai.domain.model.AIChatMessages
import kotlinx.coroutines.flow.Flow

interface AiChatRepository {

    suspend fun sendAiChatMessage(
        message: String,
        apiKey: String
    ): AIChatMessages

    fun getMessagesForSession(
        sessionId: Long
    ): Flow<List<ChatMessageEntity>>

    suspend fun insertMessage(message: ChatMessageEntity)

    suspend fun createSession(title: String): Long

    suspend fun updateLastMessage(
        sessionId: Long,
        message: String
    )

    fun getAllSessions() : Flow<List<ChatSessionEntity>>
    fun getRecentSessions() : Flow<List<ChatSessionEntity>>
    fun getSessionCount() : Flow<Int>




}

