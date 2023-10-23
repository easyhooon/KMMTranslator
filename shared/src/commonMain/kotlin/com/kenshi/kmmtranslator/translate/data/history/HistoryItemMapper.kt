package com.kenshi.kmmtranslator.translate.data.history

import com.kenshi.kmmtranslator.translate.domain.history.HistoryItem
import database.HistoryEntity

// domain Model 인 HistoryItem 은 timeStamp 정보가 필요하지 않음
// 따라서 도메인 모델로 변환 시 이를 제거할 수 있음
// 도메인 모델에는 실제로 앱 전체에서 공유해야 하는 정보만 포함되어 있음
fun HistoryEntity.toHistoryItem(): HistoryItem {
    return HistoryItem(
        id = id,
        fromLanguageCode = fromLanguageCode,
        fromText = fromText,
        toLanguageCode = toLanguageCode,
        toText = toText,
    )
}