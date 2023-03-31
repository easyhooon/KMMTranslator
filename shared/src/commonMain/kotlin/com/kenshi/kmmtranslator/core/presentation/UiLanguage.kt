package com.kenshi.kmmtranslator.core.presentation

import com.kenshi.kmmtranslator.core.domain.language.Language

expect class UiLanguage {
    val language: Language
    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}