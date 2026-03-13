package com.droidmentorai.presentation.screens.aiChatScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidmentorai.core.utils.API_KEY
import com.droidmentorai.core.utils.ROLE_ASSISTANT
import com.droidmentorai.core.utils.ROLE_USER
import com.droidmentorai.data.room.entity.ChatMessageEntity
import com.droidmentorai.domain.model.AIChatMessages
import com.droidmentorai.domain.useCase.CreateSessionUseCase
import com.droidmentorai.domain.useCase.GetMessagesUseCase
import com.droidmentorai.domain.useCase.InsertMessageUseCase
import com.droidmentorai.domain.useCase.SendMessageUseCase
import com.droidmentorai.domain.useCase.UpdateLastMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val insertMessageUseCase: InsertMessageUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    private val updateLastMessageUseCase: UpdateLastMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    private var generateJob: Job? = null

    var isGenerating by mutableStateOf(false)
        private set



    // Current conversation session
    private var currentSessionId: Long? = null

    fun sendMessage(prompt: String) {

        generateJob?.cancel()

        generateJob = viewModelScope.launch {

            isGenerating = true

            if (currentSessionId == null) {
                currentSessionId = createSessionUseCase(prompt.take(40))
                Timber.e("NEW SESSION CREATED -> ID: $currentSessionId")
            }

            val sessionId = currentSessionId ?: return@launch

            try {

                val userMessage = AIChatMessages(
                    message = prompt,
                    role = ROLE_USER
                )

                _uiState.update {
                    it.copy(
                        messages = it.messages + userMessage,
                        isLoading = true
                    )
                }

                insertMessageUseCase(
                    ChatMessageEntity(
                        sessionId = sessionId,
                        message = prompt,
                        role = ROLE_USER
                    )
                )

                updateLastMessageUseCase(sessionId, prompt)

                val response = sendMessageUseCase(
                    prompt,
                    API_KEY
                )

                handleSuccess(response, sessionId)

            }  catch (e : CancellationException) {
                //dont show in ui when generation is cancelled
                Timber.e("AI generation cancelled")
            }
            catch (e: retrofit2.HttpException) {

                if (e.code() == 429) {

                    val errorBody = e.response()?.errorBody()?.string()
                    val retrySeconds = extractRetryDelaySeconds(errorBody)

                    Timber.e("Rate limit hit. Retrying in $retrySeconds seconds")

                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = "AI is thinking... retrying in $retrySeconds seconds"
                        )
                    }

                    delay(retrySeconds * 1000)

                    val response = sendMessageUseCase(
                        prompt,
                        API_KEY
                    )

                    handleSuccess(response, sessionId)

                } else {
                    handleError(e)
                }

            } catch (e: Exception) {
                handleError(e)
            } finally {
                isGenerating = false
            }
        }
    }

    fun stopGeneration() {
        generateJob?.cancel()

        isGenerating = false

        _uiState.update {
            it.copy(isLoading = false)
        }

        Timber.e("AI generation stopped by user")
    }
    private suspend fun streamAiMessage(
        fullMessage: String,
        sessionId: Long
    ) {

        var currentText = ""

        for (char in fullMessage) {

            if (!isGenerating) break //cancel streaming on stop generate

            currentText += char

            val aiMessage = AIChatMessages(
                message = currentText,
                role = ROLE_ASSISTANT
            )

            _uiState.update { state ->

                val messages = state.messages.toMutableList()

                if (messages.isNotEmpty() &&
                    messages.last().role == ROLE_ASSISTANT
                ) {
                    messages[messages.lastIndex] = aiMessage
                } else {
                    messages.add(aiMessage)
                }

                state.copy(messages = messages)
            }

            delay(25)
        }

        insertMessageUseCase(
            ChatMessageEntity(
                sessionId = sessionId,
                message = fullMessage,
                role = ROLE_ASSISTANT
            )
        )

        updateLastMessageUseCase(sessionId, fullMessage)

        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    private fun handleError(e: Exception) {

        Timber.e(e, "Gemini request failed")

        _uiState.update {
            it.copy(
                isLoading = false,
                error = e.message
            )
        }
    }

    private suspend fun handleSuccess(
        response: AIChatMessages,
        sessionId: Long
    ) {

        streamAiMessage(
            response.message,
            sessionId
        )

    }

    private fun extractRetryDelaySeconds(errorBody: String?): Long {

        if (errorBody == null) return 30

        val regex = Regex("\"retryDelay\"\\s*:\\s*\"(\\d+)s\"")
        val match = regex.find(errorBody)

        return match?.groupValues?.get(1)?.toLongOrNull() ?: 30
    }

    fun loadSession(sessionId: Long) {
        currentSessionId = sessionId
        viewModelScope.launch {

            getMessagesUseCase(sessionId).collect { messages ->

                val chatMessages = messages.map {

                    AIChatMessages(
                        message = it.message,
                        role = it.role
                    )
                }

                _uiState.update {
                    it.copy(messages = chatMessages)
                }

                Timber.e("SESSION ID: $sessionId")
                Timber.e("MESSAGE COUNT: ${messages.size}")

                messages.forEach {
                    Timber.e("DB MESSAGE -> role: ${it.role}, text: ${it.message}")
                }
            }
        }
    }

    fun regenerateResponse() {

        val messages = _uiState.value.messages
        if (messages.isEmpty()) return

        val lastUserMessage = messages.lastOrNull { it.role == ROLE_USER } ?: return

        _uiState.update { state ->

            val updatedMessages = state.messages.toMutableList()

            if (updatedMessages.isNotEmpty() &&
                updatedMessages.last().role == ROLE_ASSISTANT
            ) {
                updatedMessages.removeAt(updatedMessages.lastIndex)
            }

            state.copy(messages = updatedMessages)
        }

        sendMessage(lastUserMessage.message)
    }
}

