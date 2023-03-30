package com.kenshi.kmmtranslator.translate.data.remote

import io.ktor.client.*

// similar with abstract class
expect class HttpClientFactory {
    fun create(): HttpClient
}