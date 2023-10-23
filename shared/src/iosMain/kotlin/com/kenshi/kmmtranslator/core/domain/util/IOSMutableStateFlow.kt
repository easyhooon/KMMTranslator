package com.kenshi.kmmtranslator.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow

// iOS에서 CommonMutableStateFlow 에 대한 생성자를 호출하려면 MutableStateFlow 를 전달 해야함
// 그리고 iOS 에는 그게 없음, 왜냐면 iOS 에서는 모든 종류의 Flow 클래스를 실제로 사용할 수 없기 때문
// 따라서 iOS에서는 MutableStateFlow 객체를 생성할 수 없음
// 이를 해결하기 위함
// 초기 값, 즉 StateFlow 로 쉽게 사용할 수 있는 초기 상태를 전달할 수 있는
// 또 다른 종류의 iOS 특정 MutableStateFlow
// 이렇게 하면 초기 값만 사용하여 iOS에서 일반적으로 MutableStateFlow 를 실제로 생성하는데, 더 이상 문제가 발생하지 않음
class IOSMutableStateFlow<T>(
    private val initialValue: T,
) : CommonMutableStateFlow<T>(MutableStateFlow(initialValue))