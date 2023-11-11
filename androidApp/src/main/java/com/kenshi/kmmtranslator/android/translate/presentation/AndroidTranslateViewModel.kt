package com.kenshi.kmmtranslator.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenshi.kmmtranslator.translate.domain.history.HistoryDataSource
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateUseCase
import com.kenshi.kmmtranslator.translate.presentation.TranslateEvent
import com.kenshi.kmmtranslator.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// KMM 프로젝트에서 Hilt 를 사용하는 이유
// 공유 모듈에선 사용할 수 없지만,
// Android 에선 Java 라이브러리를 사용하는 것을 반대할 이유는 없고, 완전히 잘 작동하기 때문
// 그럼 공유 모듈 자체와 iOS에서 이를 어떻게 관리할 것인가?
// iOS 에서는 단순히 생성자 주입을 직접 사용함
// 따라서 우리는 클래스 생성자에 모든 종속성을 제공하기만 하면 됨
// iOS 에서는 의존성 범위를 특정 화면으로 쉽게 지정할 수 있기 때문에 직접 생성자를 주입하는 방법으로 구현해도 괜찮
// Android 에선 원하는 해당 화면에서 이를 초기화할 수 있기 때문에, 화면이 다시 생성되고
// 특정 종속성도 그런식으로 다시 생성되는 구성 변경과 같은 작업이 있기 때문에 쉽지 않음
// -> iOS 에선 이런 문제가 발생하지 않음
// Koin 을 사용한다 할지라도 KMM 프로젝트 개발이 그렇게 쉬워지지는 않음
// 공유 모델 자체에 모듈을 두어 데이터 소스, HTTP 클라이언트와 같은 항목을 제공하고
// 이를 공유 모듈과 Android 앱에 주입할 수 있음
// 하지만 한가지 문제는 Android 에서 의존성 주입을 koin으로 해야 한다는 것
// Android 개발을 할때 Hilt 로 의존성 주입을 하는 것을 더 선호하고
// 다른 Jetpack 라이브러리들과도 호환(통합)이 좋음 ex) HiltViewModel
// Koin 에도 비슷한 기능이 있다곤 하는데 좋은 경험을 하지 못함
// 그리고 savedStateHandle 이 제대로 주입되지 않는 문제들이 종종 발생했었음(경험)
// 또 한가지 문제는 Koin 을 사용한다해도 iOS에 의존성을 주입하는데 사용할 수 없음
// iOS 에서는 코틀린의 특정 함수에 접근할 수 없음
// val database by inject() 를 통해 객체를 생성하고 참조하는 것이 작동하지 않음
// 따라서 iOS 에서는 여전히 수동 생성자 주입이 필요 당연하게도 이런 프레임워크가 iOS 용이 아니기 때문
// 뭐 언젠간 iOS 의존성을 쉽게 주입할 수 있는 솔루션이 나오지 않는 이상
// Android 에선 의존성 주입을 Hilt로, 나머지 영역에선 수동 생성자 주입을 사용하는 것으로
// Koin 이 의미 있다고 보는 유일한 시나리오는 공유 모듈에 많은 test-sui 이 있는 경우
// 많은 종속성이 필요한 테스트가 많고, 모든 단일 테스트 클래스에 대한 종속성을 관리해야 하는 경우
// 항상 수동으로 인스턴스화할 필요는 없으므로 이땐, koin 을 사용 하는게 합리적

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope,
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}