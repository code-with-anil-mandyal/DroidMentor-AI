package com.droidmentorai.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droidmentorai.data.room.dao.ChatDao
import com.droidmentorai.data.room.dao.ChatSessionDao
import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.data.room.entity.ChatSessionEntity

@Database(
    entities = [
        ChatMessageEntity::class,
        ChatSessionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    abstract fun chatSessionDao(): ChatSessionDao
}