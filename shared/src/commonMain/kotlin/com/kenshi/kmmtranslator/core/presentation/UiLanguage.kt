package com.kenshi.kmmtranslator.core.presentation

import com.kenshi.kmmtranslator.core.domain.language.Language

// model 마다 차이가 있기 때문에 Language 모델 외에 별도의 UiLanguage 모델이 필요
// lanugage 와 함께 표시되는 벡터 이미지는 단지 presentation 과 관련된 것
// 이는 domain 에 포함 되어서는 안됨
// UiHistoryItem 도 마찬가지
expect class UiLanguage {
    val language: Language
    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}