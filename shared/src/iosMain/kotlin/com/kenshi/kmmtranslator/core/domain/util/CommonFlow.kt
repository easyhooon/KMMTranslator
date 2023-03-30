package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// iOS 에서는 flow 를 사용하는데 제약이 있기 때문에 해당 작업들을 수행
// brandi labs 블로그 에서 언급한 단점
actual open class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
): Flow<T> by flow {
    // main dispatcher 를 제외하곤 사용할 수 없음
    fun subscribe(
        coroutineScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        onCollect: (T) -> Unit
    ): DisposableHandle {
        val job = coroutineScope.launch(dispatcher) {
            flow.collect(onCollect)
        }
//        return object: DisposableHandle {
//            override fun dispose() {
//                job.cancel()
//            }
//        }
        //TODO DisposableHandle 에 대해 학습
        return DisposableHandle { job.cancel() }
    }
}
