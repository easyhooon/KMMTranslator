package com.kenshi.kmmtranslator.translate.presentation

import com.kenshi.kmmtranslator.core.presentation.UiLanguage

// 이미지 정보를 포함하는 HistoryItem
data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage,
)
