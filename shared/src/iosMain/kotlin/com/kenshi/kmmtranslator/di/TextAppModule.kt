package com.kenshi.kmmtranslator.di

import com.kenshi.kmmtranslator.testing.FakeHistoryDataSource
import com.kenshi.kmmtranslator.testing.FakeTranslateClient
import com.kenshi.kmmtranslator.testing.FakeVoiceToTextParser
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateClient
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateUseCase

class TestAppModule : AppModule {
    override val historyDataSource = FakeHistoryDataSource()
    override val client: TranslateClient = FakeTranslateClient()
    override val translateUseCase = TranslateUseCase(client, historyDataSource)
    override val voiceParser = FakeVoiceToTextParser()
}