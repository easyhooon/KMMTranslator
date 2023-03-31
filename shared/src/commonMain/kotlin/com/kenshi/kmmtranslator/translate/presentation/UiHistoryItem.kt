package com.kenshi.kmmtranslator.translate.presentation

import com.kenshi.kmmtranslator.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)
