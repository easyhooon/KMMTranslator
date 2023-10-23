package com.kenshi.kmmtranslator.core.domain.util

// kotlin Result 와 같은 역할을 하는 클래스
// kotlin Result 는 iOS 에서 처리하기가 조금 더 어려워지므로 Wrapper 클래스를 직접 만듬
sealed class Resource<T>(val data: T?, val throwable: Throwable? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(throwable: Throwable): Resource<T>(null, throwable)
}
