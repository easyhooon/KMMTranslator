package com.kenshi.kmmtranslator.translate.data.translate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// JSON 을 이용한 통신을 하기 위해 Serializable 어노테이션이 필요
// kotlin 클래스 -> JSON (직렬화)
// JSON -> kotlin 클래스 (역직렬화)
@Serializable
data class TranslateDto(
    @SerialName("q")
    val textToTranslate: String,

    @SerialName("source")
    val sourceLanguageCode: String,

    @SerialName("target")
    val targetLanguageCode: String,
)
