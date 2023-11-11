package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// iOS 에서는 flow 를 사용하는데 제약이 있기 때문에 해당 작업들을 수행
// brandi labs 블로그 에서 언급한 단점
// Android 쪽에서는 작업을 flow 에 위임하지만 iOS 쪽에서는 실제로 구현을 해야함
// iOS 에서는 코루틴 디스패처가 없기 때문에 Android 에서 처럼 Flow를 수집할 수 없음
actual open class CommonFlow<T> actual constructor(
    private val flow: Flow<T>,
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
        // 다른 코드로 대체
        // Kotlin 프레임워크에서 직접적으로 제공되는 Kotlin 특정 클래스를 iOS에 노출하고 싶지 않음
        // iOS 에서는 이를 제대로 처리할 수 없고, 모든 기능에 엑세스 할 수 없기 때문
//        return object: DisposableHandle {
//            override fun dispose() {
//                job.cancel()
//            }
//        }
        // DisposableHandle 에 대해 학습
        // Kotlin에서 disposableHandle은 보통 코루틴의 생명주기와 관련된 리소스 정리에 사용되는 개념.
        // 코루틴을 사용할 때 외부 리소스나 로그, 네트워크 연결과 같은 것들을 사용하게 되는데,
        // 코루틴이 끝났을 때 이러한 리소스들을 안전하게 정리해야 함.
        // 이를 위해 disposableHandle이 사용됩니다.
        // disposableHandle은 코루틴 스코프 내에 있으며, 해당 스코프가 취소되거나 완료되면 정의된 작업(리소스 해제 등)을 실행하는 메커니즘을 제공합니다.
        // 이러한 handle은 CoroutineScope 내에서 사용되며, 주로 launch나 async와 같은 코루틴 빌더를 사용하여 코루틴을 시작할 때 얻을 수 있습니다.
        return DisposableHandle { job.cancel() }
    }

    fun subscribe(
        onCollect: (T) -> Unit
    ): DisposableHandle {
        return subscribe(
            coroutineScope = GlobalScope,
            dispatcher = Dispatchers.Main,
            onCollect = onCollect,
        )
    }
}
