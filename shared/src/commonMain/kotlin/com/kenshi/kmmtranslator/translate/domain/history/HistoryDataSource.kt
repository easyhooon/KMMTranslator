package com.kenshi.kmmtranslator.translate.domain.history

import com.kenshi.kmmtranslator.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(item: HistoryItem)
}