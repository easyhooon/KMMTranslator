package com.kenshi.kmmtranslator.voice_to_text.domain

data class VoiceToTextParserState(
    val result: String = "",
    val error: String? = null,
    val powerRatio: Float = 0f, // 0 ~ 1, 1로 갈수록 큰 소리(100%)
    val isSpeaking: Boolean = false,
)
