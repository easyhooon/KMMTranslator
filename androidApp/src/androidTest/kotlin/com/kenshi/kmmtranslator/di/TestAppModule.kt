package com.kenshi.kmmtranslator.di

import com.kenshi.kmmtranslator.translate.data.local.FakeHistoryDataSource
import com.kenshi.kmmtranslator.translate.data.remote.FakeTranslateClient
import com.kenshi.kmmtranslator.translate.domain.history.HistoryDataSource
import com.kenshi.kmmtranslator.translate.domain.translate.Translate
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateClient
import com.kenshi.kmmtranslator.voice_to_text.data.FakeVoiceToTextParser
import com.kenshi.kmmtranslator.voice_to_text.domain.VoiceToTextParser
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource {
        return FakeHistoryDataSource()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): Translate {
        return Translate(client, dataSource)
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser {
        return FakeVoiceToTextParser()
    }
}
