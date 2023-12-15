package com.kenshi.kmmtranslator.testing

import com.kenshi.kmmtranslator.core.domain.language.Language
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateClient

class FakeTranslateClient: TranslateClient {

    var translatedText = "test translation"
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }
}
