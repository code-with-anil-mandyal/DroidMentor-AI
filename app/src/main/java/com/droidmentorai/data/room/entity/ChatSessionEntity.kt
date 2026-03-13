package com.droidmentorai.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_sessions")
data class ChatSessionEntity(

    @PrimaryKey(autoGenerate = true)
    val sessionId: Long = 0,

    val title: String,

    val createdAt: Long = System.currentTimeMillis(),

    val lastMessage: String? = null,

    val lastMessageTime: Long? = null
)