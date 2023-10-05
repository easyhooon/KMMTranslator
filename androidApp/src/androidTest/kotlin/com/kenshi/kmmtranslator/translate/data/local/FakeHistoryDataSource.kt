package com.kenshi.kmmtranslator.translate.data.local

import com.kenshi.kmmtranslator.core.domain.util.CommonFlow
import com.kenshi.kmmtranslator.core.domain.util.toCommonFlow
import com.kenshi.kmmtranslator.translate.domain.history.HistoryDataSource
import com.kenshi.kmmtranslator.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Collections.emptyList

class FakeHistoryDataSource: HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        _data.value += item
    }
}
