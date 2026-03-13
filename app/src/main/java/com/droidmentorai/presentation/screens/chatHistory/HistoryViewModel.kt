package com.droidmentorai.presentation.screens.chatHistory

import androidx.lifecycle.ViewModel
import com.droidmentorai.domain.useCase.GetSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase
) : ViewModel() {
    val sessions = getSessionsUseCase()
}