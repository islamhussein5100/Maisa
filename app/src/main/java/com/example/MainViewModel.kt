package com.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _messages = MutableStateFlow<List<ChatMessage>>(listOf(initialWelcomeMessage()))
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isThinking = MutableStateFlow(false)
    val isThinking: StateFlow<Boolean> = _isThinking.asStateFlow()

    private val _autoSpeak = MutableStateFlow(false)
    val autoSpeak: StateFlow<Boolean> = _autoSpeak.asStateFlow()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private fun initialWelcomeMessage(): ChatMessage {
        return ChatMessage(
            sender = MessageSender.MAYA,
            text = "Hello! I am Maya, your personal assistant. How can I help you today? You can ask me questions, request productivity tips, check device battery, calculate math, or tap the microphone to speak!"
        )
    }

    fun sendMessage(text: String, onResponseReady: ((String) -> Unit)? = null) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return

        val userMessage = ChatMessage(sender = MessageSender.USER, text = trimmed)
        _messages.value = _messages.value + userMessage
        _isThinking.value = true

        viewModelScope.launch {
            // Brief simulation delay for smooth conversational experience
            delay(400)
            val context = getApplication<Application>().applicationContext
            val responseText = AssistantCore.processQuery(context, trimmed)
            val assistantMessage = ChatMessage(sender = MessageSender.MAYA, text = responseText)
            _messages.value = _messages.value + assistantMessage
            _isThinking.value = false

            if (_autoSpeak.value) {
                onResponseReady?.invoke(responseText)
            }
        }
    }

    fun clearChat() {
        _messages.value = listOf(initialWelcomeMessage())
        _isThinking.value = false
    }

    fun toggleAutoSpeak() {
        _autoSpeak.value = !_autoSpeak.value
    }

    fun setListening(listening: Boolean) {
        _isListening.value = listening
    }
}
