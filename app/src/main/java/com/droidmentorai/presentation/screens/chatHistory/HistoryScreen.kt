package com.droidmentorai.presentation.screens.chatHistory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidmentorai.R
import com.droidmentorai.presentation.components.Header
import com.droidmentorai.presentation.screens.home.ChatHistorySection

@Composable
fun HistoryScreen(
    onBackClick : () -> Unit,
    viewModel: HistoryViewModel = hiltViewModel(),
    onChatClick: (Long) -> Unit
) {


    val session by viewModel.sessions.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        Header(
            title = stringResource(id = R.string.chat_history),
            onBackClick = onBackClick,
            isShowBack = true,
            backIcon = Icons.AutoMirrored.Default.ArrowBack
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            //List Section
            items(session){  session ->

                ChatHistorySection(session = session, onClick = { sessionId ->
                    onChatClick(sessionId)
                })
            }
        }

    }
}