package com.droidmentorai.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.droidmentorai.domain.useCase.GetRecentUseCase
import com.droidmentorai.domain.useCase.GetSessionCountUseCase
import com.droidmentorai.domain.useCase.GetSessionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecentUseCase: GetRecentUseCase,
    private val getSessionCountUseCase: GetSessionCountUseCase
) : ViewModel() {

    val recentSessions = getRecentUseCase()
    val sessionCount = getSessionCountUseCase()



}