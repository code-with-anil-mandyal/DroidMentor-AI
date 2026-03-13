package com.droidmentorai.di

import com.droidmentorai.data.api.ApiRoutes.BASE_URL
import com.droidmentorai.data.api.ApiService
import com.droidmentorai.data.api.NetworkConfig.DEFAULT_TIMEOUT_SEC
import com.droidmentorai.data.repository.AiChatRepositoryImpl
import com.droidmentorai.data.room.dao.ChatDao
import com.droidmentorai.data.room.dao.ChatSessionDao
import com.droidmentorai.domain.repository.AiChatRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient,  gsonConverterFactory: GsonConverterFactory) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()


    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideChatRepository(apiService: ApiService, chatDao: ChatDao, sessionDao: ChatSessionDao) : AiChatRepository{
        return AiChatRepositoryImpl(apiService, chatDao, sessionDao)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
}