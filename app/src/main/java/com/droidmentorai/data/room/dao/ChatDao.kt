package com.droidmentorai.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.data.room.entity.ChatSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert
    suspend fun insertMessage(message: ChatMessageEntity)

    @Query("""
        SELECT * FROM chat_messages
        WHERE sessionId = :sessionId
        ORDER BY timestamp ASC
    """)
    fun getMessagesForSession(sessionId: Long): Flow<List<ChatMessageEntity>>

    @Query("DELETE FROM chat_messages WHERE sessionId = :sessionId")
    suspend fun deleteMessagesForSession(sessionId: Long)

}