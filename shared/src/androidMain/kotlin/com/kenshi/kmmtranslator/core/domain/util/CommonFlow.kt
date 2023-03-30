package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.Flow

// android 단에서는 당연히 flow 를 사용하는데 문제가 없음
actual class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow
