package com.kenshi.kmmtranslator.voice_to_text.domain

import com.kenshi.kmmtranslator.core.domain.util.CommonStateFlow
import com.plcoding.translator_kmm.voice_to_text.domain.VoiceToTextParserState

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel()
    fun reset()
}