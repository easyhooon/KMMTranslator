package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.StateFlow

//iOS 에서 flow 를 사용하기 위한 flow wrapping 작업
expect class CommonStateFlow<T>(flow: StateFlow<T>): StateFlow<T>

fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)