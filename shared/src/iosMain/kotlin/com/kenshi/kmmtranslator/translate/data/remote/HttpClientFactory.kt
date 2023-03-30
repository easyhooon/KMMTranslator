package com.kenshi.kmmtranslator.translate.data.remote

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

actual class HttpClientFactory {
    actual fun create(): HttpClient {
        // ios 는 Darwin
        return HttpClient(Darwin) {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}