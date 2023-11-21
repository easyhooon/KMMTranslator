package com.kenshi.kmmtranslator.voice_to_text.presentation

import com.kenshi.kmmtranslator.core.domain.util.toCommonStateFlow
import com.kenshi.kmmtranslator.voice_to_text.domain.VoiceToTextParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VoiceToTextViewModel(
    private val parser: VoiceToTextParser,
    coroutineScope: CoroutineScope? = null,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(VoiceToTextState())

    // 상태를 정의할 때 이렇게 할 수 도 있다.
    val state = _state.combine(parser.state) { state, voiceResult ->
        state.copy(
            spokenText = voiceResult.result,
            recordError = if (state.canRecord) {
                voiceResult.error
            } else {
                "Can't record without permission"
            },
            displayState = when {
                !state.canRecord || voiceResult.error != null -> DisplayState.ERROR
                voiceResult.result.isNotBlank() && !voiceResult.isSpeaking -> {
                    DisplayState.DISPLAYING_RESULTS
                }

                voiceResult.isSpeaking -> DisplayState.SPEAKING
                else -> DisplayState.WAITING_TO_TALK
            }
        )
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VoiceToTextState())
        // iOS 에서도 flow 를 수집할 수 있도록
        .toCommonStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                if (state.value.displayState == DisplayState.SPEAKING) {
                    _state.update {
                        it.copy(powerRatios = it.powerRatios + parser.state.value.powerRatio)
                    }
                }
                delay(50L)
            }
        }
    }

    // TODO 언제는 is 가 붙고 언제는 그냥인 이유가 뭘까
    fun onEvent(event: VoiceToTextEvent) {
        when (event) {
            is VoiceToTextEvent.PermissionResult -> {
                _state.update {
                    it.copy(canRecord = event.isGranted)
                }
            }

            VoiceToTextEvent.Reset -> {
                parser.reset()
                _state.update { VoiceToTextState() }
            }

            is VoiceToTextEvent.ToggleRecording -> toggleRecording(event.languageCode)
            else -> Unit
        }
    }

    private fun toggleRecording(languageCode: String) {
        _state.update {
            it.copy(powerRatios = emptyList())
        }
        parser.cancel()
        if (state.value.displayState == DisplayState.SPEAKING) {
            parser.stopListening()
        } else {
            parser.startListening(languageCode)
        }
    }
}