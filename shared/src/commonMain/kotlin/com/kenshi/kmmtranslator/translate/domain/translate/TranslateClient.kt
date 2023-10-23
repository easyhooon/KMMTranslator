package com.kenshi.kmmtranslator.translate.domain.translate

import com.kenshi.kmmtranslator.core.domain.language.Language

// TraslateClient 를 쉽게 사용할 수 있도록 추상화
// Ktor 가 아닌 다른 HttpClient 을 사용하게 되었을 경우 presentation 과 domain 계층은 은 변해선 안되고,
// data 계층 만 변경할 수 있어야 하기 때문에
// 이것이 우리가 도메인에서 추상화(interface)를 만드는 이유
interface  TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language,
    ): String
}