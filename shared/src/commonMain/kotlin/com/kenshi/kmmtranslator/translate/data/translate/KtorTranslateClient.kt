package com.kenshi.kmmtranslator.translate.data.translate

import com.kenshi.kmmtranslator.NetworkConstants
import com.kenshi.kmmtranslator.core.domain.language.Language
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateClient
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateError
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

// share Network Call & Error message between Android & iOS
class KtorTranslateClient(
    private val httpClient: HttpClient
) : TranslateClient {

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language,
    ): String {
        val result = try {
            httpClient.post {
                url(NetworkConstants.BASE_URL + "/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = fromText,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode,
                    )
                )
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateError.SERVICE_UNAVAILABLE)
        }

        // 에러를 누락되는 경우 없이 핸들링 하는 방법!
        when(result.status.value) {
            in 200..299 -> Unit

            500 -> throw TranslateException(TranslateError.SERVER_ERROR)

            in 400..499 -> throw TranslateException(TranslateError.CLIENT_ERROR)

            else -> throw TranslateException(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        } catch (e: Exception) {
            throw TranslateException(TranslateError.SERVER_ERROR)
        }
    }
}