package com.kenshi.kmmtranslator.core.domain.util

// 대체된 코드
// 이제 iOS 측 Swift 코드에서 안전하게 사용할 수 있음
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