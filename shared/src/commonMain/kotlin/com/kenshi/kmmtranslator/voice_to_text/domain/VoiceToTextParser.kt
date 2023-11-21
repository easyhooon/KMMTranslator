package com.kenshi.kmmtranslator.voice_to_text.domain

import com.kenshi.kmmtranslator.core.domain.util.CommonStateFlow

// 뷰모델에서 직접 초기화 되지 않음
interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}