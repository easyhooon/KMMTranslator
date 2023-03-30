package com.kenshi.kmmtranslator.core.domain.util

//TODO 이런 문법도 되는구나 (함수형 인터페이스)

fun interface DisposableHandle: kotlinx.coroutines.DisposableHandle

// 아래의 코드와 같은 역할
//interface DisposableHandle: kotlinx.coroutines.DisposableHandle
//
//fun DisposableHandle(block: () -> Unit): DisposableHandle {
//    return object : DisposableHandle {
//        override fun dispose() {
//            block()
//        }
//    }
//}