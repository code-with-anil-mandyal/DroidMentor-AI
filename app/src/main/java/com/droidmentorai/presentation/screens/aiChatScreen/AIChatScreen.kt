package com.droidmentorai.presentation.screens.aiChatScreen


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidmentorai.R
import com.droidmentorai.core.utils.ChatMode
import com.droidmentorai.core.utils.ROLE_USER
import com.droidmentorai.domain.model.AIChatMessages
import com.droidmentorai.presentation.components.AiTypingIndicator
import com.droidmentorai.presentation.components.Header
import com.droidmentorai.presentation.ui.theme.AiChatBubble
import com.droidmentorai.presentation.ui.theme.UserChatBubble
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.launch

@Composable
fun AiChatScreen(
    onBackClick: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel(),
    sessionId: Long? = 0,
    mode : ChatMode = ChatMode.ASK
) {

    var message by remember { mutableStateOf("") }

    val state by viewModel.uiState.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val placeholder = when(mode) {

        ChatMode.ASK ->
            "Ask anything about Android..."

        ChatMode.GENERATE_CODE ->
            "Describe the code you want..."

        ChatMode.EXPLAIN_CODE ->
            "Paste code to explain..."


        ChatMode.JAVA_TO_KOTLIN ->
            "Paste Java code..."
    }

    val title = when(mode) {

        ChatMode.ASK -> "Ask AI"
        ChatMode.GENERATE_CODE -> "Generate Code"
        ChatMode.EXPLAIN_CODE -> "Explain Code"
        ChatMode.JAVA_TO_KOTLIN -> "Java → Kotlin"
    }

    LaunchedEffect(sessionId) {
        sessionId?.let {
            viewModel.loadSession(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        Header(
            title = title,
            onBackClick = onBackClick,
            isShowBack = true,
            backIcon = Icons.AutoMirrored.Default.ArrowBack
        )

        ChatMessages(
            messages = state.messages,
            modifier = Modifier.weight(1f),
            viewModel = viewModel
        )

        ChatInputField(
            message = message,
            placeholder = placeholder,
            viewModel = viewModel,
            onMessageChange = { message = it },
            onSendClick = {

                if (message.isNotBlank()) {

                    focusManager.clearFocus()
                    keyboardController?.hide()

                    //viewModel.sendMessage(message)
                    sendMessage(viewModel, message, mode)
                    message = ""
                }
            }
        )
    }
}


fun sendMessage(
    viewModel: ChatViewModel,
    message: String,
    mode : ChatMode = ChatMode.ASK
){

    val finalPrompt = when(mode) {

        ChatMode.ASK -> message

        ChatMode.GENERATE_CODE ->
            "Generate production-ready Android code for:\n$message"

        ChatMode.EXPLAIN_CODE ->
            "Explain the following code step by step:\n$message"
        

        ChatMode.JAVA_TO_KOTLIN ->
            "Convert the following Java code into Kotlin:\n$message"
    }

    viewModel.sendMessage(finalPrompt)
}

@Composable
fun ChatMessages(
    messages: List<AIChatMessages>,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem >= layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.scrollToItem(messages.lastIndex)
        }
    }

    Box(modifier = modifier) {

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        ) {

            itemsIndexed(messages) { index, message ->

                if (message.role == ROLE_USER) {

                    UserMessageBubble(message.message)

                } else {

                    AiMessageBubble(message.message)

                    val isLastMessage = index == messages.lastIndex

                    if (isLastMessage && !viewModel.isGenerating) {
                        RegenerateButton(viewModel)
                    }
                }
            }

            if (viewModel.isGenerating) {
                item {
                    AiTypingIndicator(
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        if (!isAtBottom) {

            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(messages.lastIndex)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowDown, null)
            }
        }
    }
}

@Composable
fun RegenerateButton(viewModel: ChatViewModel) {

    Row(
        modifier = Modifier.padding(start = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = { viewModel.regenerateResponse() }
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_regenerate_response),
                contentDescription = "Regenerate",
                tint = Color.LightGray
            )
        }

        Text(
            text = "Regenerate",
            fontSize = 13.sp,
            color = Color.LightGray
        )
    }
}

@Composable
fun UserMessageBubble(text: String) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {

        Text(
            text = text,
            modifier = Modifier
                .background(
                    UserChatBubble,
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            color = Color.White
        )
    }
}


@Composable
fun AiMessageBubble(text: String) {

    val context = LocalContext.current

    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {

        Column(
            modifier = Modifier
                .background(
                    AiChatBubble,
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {

            Box(
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {

                MarkdownText(
                    markdown = text,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_copy),
                    contentDescription = "Copy",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {

                            val clip = ClipData.newPlainText(
                                "AI message",
                                text
                            )

                            clipboardManager.setPrimaryClip(clip)

                            Toast.makeText(
                                context,
                                "Copied to clipboard",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                )
            }
        }
    }
}



@Composable
fun ChatInputField(
    message: String,
    placeholder: String,
    viewModel: ChatViewModel,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .background(
                Color.White,
                RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = message,
            onValueChange = onMessageChange,
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(placeholder)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 4
        )

        if (viewModel.isGenerating) {

            IconButton(
                onClick = { viewModel.stopGeneration() }
            ) {

                Icon(
                    painter = painterResource(R.drawable.outline_stop_circle_24),
                    contentDescription = "Stop",
                    tint = Color.Black
                )
            }

        } else {

            IconButton(
                onClick = onSendClick
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = "Send",
                    tint = Color.Black
                )
            }
        }
    }
}


