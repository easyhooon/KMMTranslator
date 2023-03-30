package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.Flow

expect class CommonFlow<T>(flow: Flow<T>): Flow<T>

// for iOS
fun <T> Flow<T>.toCommonFlow() = CommonFlow(this)