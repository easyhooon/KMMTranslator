package com.kenshi.kmmtranslator.di

import com.kenshi.kmmtranslator.database.TranslateDatabase
import com.kenshi.kmmtranslator.translate.data.history.SqlDelightHistoryDataSource
import com.kenshi.kmmtranslator.translate.data.local.DatabaseDriverFactory
import com.kenshi.kmmtranslator.translate.data.remote.HttpClientFactory
import com.kenshi.kmmtranslator.translate.data.translate.KtorTranslateClient
import com.kenshi.kmmtranslator.translate.domain.history.HistoryDataSource
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateClient
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateUseCase

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }
}