package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

// iOS 에서는 flow 를 사용하는데 제약이 있기 때문에 해당 작업들을 수행
actual open class CommonStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
): CommonFlow<T>(flow), StateFlow<T> {
    /**
     * A snapshot of the replay cache.
     */
    override val replayCache: List<T>
        get() = flow.replayCache

    /**
     * The current value of this state flow.
     */
    override val value: T
        get() = flow.value

    /**
     * Accepts the given [collector] and [emits][FlowCollector.emit] values into it.
     * To emit values from a shared flow into a specific collector, either `collector.emitAll(flow)` or `collect { ... }`
     * SAM-conversion can be used.
     *
     * **A shared flow never completes**. A call to [Flow.collect] or any other terminal operator
     * on a shared flow never completes normally.
     *
     * @see [Flow.collect] for implementation and inheritance details.
     */
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        flow.collect(collector)
    }
}