package com.droidmentorai.data.repository

import com.droidmentorai.core.utils.ROLE_ASSISTANT
import com.droidmentorai.core.utils.ROLE_USER
import com.droidmentorai.data.api.ApiService
import com.droidmentorai.data.mapper.GeminiChatMapper
import com.droidmentorai.data.request.Content
import com.droidmentorai.data.request.GeminiRequest
import com.droidmentorai.data.request.Part
import com.droidmentorai.data.room.dao.ChatDao
import com.droidmentorai.data.room.dao.ChatSessionDao
import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.data.room.entity.ChatSessionEntity
import com.droidmentorai.domain.model.AIChatMessages
import com.droidmentorai.domain.repository.AiChatRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class AiChatRepositoryImpl(
    private val apiService: ApiService,
    private val chatDao: ChatDao,
    private val chatSessionDao: ChatSessionDao
) : AiChatRepository {



    override suspend fun sendAiChatMessage(
        prompt: String,
        apiKey: String
    ): AIChatMessages {

//        val contents = mutableListOf<Content>()
//
//        // send last messages as context
//        history.takeLast(1).forEach {
//
//            contents.add(
//                Content(
//                    role = it.role,
//                    parts = listOf(
//                        Part(it.message)
//                    )
//                )
//            )
//        }
//
//        contents.add(
//            Content(
//                role = ROLE_USER,
//                parts = listOf(
//                    Part(prompt)
//                )
//            )
//        )
//
//        val request = GeminiRequest(
//            contents = contents
//        )
//
//        val response = apiService.generateContent(
//            apiKey = apiKey,
//            request = request
//        )
//
//        return GeminiChatMapper.mapToDomain(response)

     //   try {


//        val contents = buildConversation(history, prompt)
//
//        val request = GeminiRequest(
//            contents = contents
//        )
//
//        Timber.e("Gemini request contents = $contents")
//
//        val response = apiService.generateContent(
//            apiKey = apiKey,
//            request = request
//        )
//
//        return GeminiChatMapper.mapToDomain(response)
//        } catch (e: Exception) {
//
//            Timber.e(e, "Gemini request failed")
//
//            throw e
//        }

        val request = GeminiRequest(
            contents = listOf(
                Content(
                    role = ROLE_USER,
                    parts = listOf(
                        Part(prompt)
                    )
                )
            )
        )

        val response = apiService.generateContent(
            apiKey = apiKey,
            request = request
        )

        return GeminiChatMapper.mapToDomain(response)

    }

    override fun getMessagesForSession(
        sessionId: Long
    ): Flow<List<ChatMessageEntity>> {
        return chatDao.getMessagesForSession(sessionId)
    }

    override suspend fun insertMessage(message: ChatMessageEntity) {
        chatDao.insertMessage(message)
    }

    override suspend fun createSession(title: String): Long {
        return chatSessionDao.insertSession(
            ChatSessionEntity(
                title = title
            )
        )
    }

    override suspend fun updateLastMessage(
        sessionId: Long,
        message: String
    ) {
        chatSessionDao.updateLastMessage(
            sessionId,
            message,
            System.currentTimeMillis()
        )
    }

    //get 5 recent chat session
    override fun getAllSessions(): Flow<List<ChatSessionEntity>> {
        return chatSessionDao.getAllSessions()

    }

    override fun getRecentSessions(): Flow<List<ChatSessionEntity>> {
        return chatSessionDao.getRecentSessions()
    }

    override fun getSessionCount(): Flow<Int> {
       return chatSessionDao.getSessionCount()
    }


    fun buildConversation(
        history: List<AIChatMessages>,
        prompt: String
    ): List<Content> {

        val contents = mutableListOf<Content>()

        val contextMessages = history.takeLast(6)

        contextMessages.forEach {

            val role = if (it.role == "assistant") {
                ROLE_ASSISTANT
            } else {
                ROLE_USER
            }

            contents.add(
                Content(
                    role = role,
                    parts = listOf(
                        Part(it.message)
                    )
                )
            )
        }

        // Prevent duplicate user messages
        if (contents.lastOrNull()?.role == ROLE_USER) {
            contents.removeLast()
        }

        contents.add(
            Content(
                role = ROLE_USER,
                parts = listOf(
                    Part(prompt)
                )
            )
        )

        return contents
    }
}