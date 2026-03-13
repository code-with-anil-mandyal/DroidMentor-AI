package com.droidmentorai.di

import android.content.Context
import androidx.room.Room
import com.droidmentorai.data.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ) : AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chat_database"
        ).build()

    }

    @Provides
    @Singleton
    fun provideChatDao(database: AppDatabase) = database.chatDao()

    @Provides
    @Singleton
    fun provideChatSessionDao(database: AppDatabase) = database.chatSessionDao()



}