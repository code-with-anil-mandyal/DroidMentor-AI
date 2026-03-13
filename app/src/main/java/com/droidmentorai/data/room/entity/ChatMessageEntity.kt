package com.droidmentorai.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "chat_messages",
    foreignKeys = [
        ForeignKey(
            entity = ChatSessionEntity::class,
            parentColumns = ["sessionId"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["sessionId"])]
)
data class ChatMessageEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val sessionId: Long,

    val message: String,

    val role: String,

    val timestamp: Long = System.currentTimeMillis()
)
