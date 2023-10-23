package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.Flow

// iOS 에선 Android 에서와 같이 Flow 와 Coroutine 이 정상적으로 지원되지 않음
// 이를 delegation(위임)의 방법을 이용 하여 해결
expect class CommonFlow<T>(flow: Flow<T>): Flow<T>

// for iOS
fun <T> Flow<T>.toCommonFlow() = CommonFlow(this)