package com.droidmentorai.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.droidmentorai.data.room.entity.ChatSessionEntity
import com.droidmentorai.data.room.model.ChatSessionWithMessages
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatSessionDao {

    @Insert
    suspend fun insertSession(session: ChatSessionEntity): Long

    @Query("SELECT * FROM chat_sessions ORDER BY lastMessageTime DESC")
    fun getAllSessions(): Flow<List<ChatSessionEntity>>

    @Query("""
        UPDATE chat_sessions 
        SET lastMessage = :message,
            lastMessageTime = :time
        WHERE sessionId = :sessionId
    """)
    suspend fun updateLastMessage(
        sessionId: Long,
        message: String,
        time: Long
    )

    @Query("DELETE FROM chat_sessions")
    suspend fun clearSessions()

    //last five chats
    @Query("SELECT * FROM chat_sessions ORDER BY lastMessageTime DESC LIMIT 5")
    fun getRecentSessions(): Flow<List<ChatSessionEntity>>

    @Query("SELECT COUNT(*) FROM chat_sessions")
    fun getSessionCount(): Flow<Int>

}