package com.droidmentorai.data.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.data.room.entity.ChatSessionEntity

data class ChatSessionWithMessages(

    @Embedded
    val session: ChatSessionEntity,

    @Relation(
        parentColumn = "sessionId",
        entityColumn = "sessionId"
    )
    val messages: List<ChatMessageEntity>
)
