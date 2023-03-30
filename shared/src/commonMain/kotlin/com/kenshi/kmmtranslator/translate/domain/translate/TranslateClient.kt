package com.kenshi.kmmtranslator.translate.domain.translate

import com.kenshi.kmmtranslator.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}