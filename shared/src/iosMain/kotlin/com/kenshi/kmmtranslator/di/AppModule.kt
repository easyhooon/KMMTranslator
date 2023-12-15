package com.kenshi.kmmtranslator.di

import com.kenshi.kmmtranslator.database.TranslateDatabase
import com.kenshi.kmmtranslator.translate.data.history.SqlDelightHistoryDataSource
import com.kenshi.kmmtranslator.translate.data.local.DatabaseDriverFactory
import com.kenshi.kmmtranslator.translate.data.remote.HttpClientFactory
import com.kenshi.kmmtranslator.translate.data.translate.KtorTranslateClient
import com.kenshi.kmmtranslator.translate.domain.history.HistoryDataSource
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateClient
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateUseCase
import com.kenshi.kmmtranslator.voice_to_text.domain.VoiceToTextParser

interface AppModule  {
    val historyDataSource: HistoryDataSource
    val client: TranslateClient
    val translateUseCase: TranslateUseCase
    val voiceParser: VoiceToTextParser
}

class AppModuleImpl(
    parser: VoiceToTextParser
): AppModule {

    override val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    override val client: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    override val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(client, historyDataSource)
    }

    override val voiceParser = parser
}