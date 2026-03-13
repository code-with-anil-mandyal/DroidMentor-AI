package com.droidmentorai.presentation.screens.aiChatScreen

import com.droidmentorai.domain.model.AIChatMessages

data class ChatUiState(
    val messages: List<AIChatMessages> = emptyList(),

    val isLoading: Boolean = false,

    val error: String? = null
)