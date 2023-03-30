package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

// android 단에서는 당연히 flow 를 사용하는데 문제가 없음
actual class CommonMutableStateFlow<T> actual constructor(
    private val flow: MutableStateFlow<T>
) : MutableStateFlow<T> by flow
