package com.kenshi.kmmtranslator.presentation

import android.Manifest
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.kenshi.kmmtranslator.android.MainActivity
import com.kenshi.kmmtranslator.android.R
import com.kenshi.kmmtranslator.android.di.AppModule
import com.kenshi.kmmtranslator.translate.data.remote.FakeTranslateClient
import com.kenshi.kmmtranslator.translate.domain.translate.TranslateClient
import com.kenshi.kmmtranslator.voice_to_text.data.FakeVoiceToTextParser
import com.kenshi.kmmtranslator.voice_to_text.domain.VoiceToTextParser
import com.plcoding.translator_kmm.android.voice_to_text.di.VoiceToTextModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@HiltAndroidTest
// AppModule과 VoiceToTextModule 모듈을 제거하여 테스트하는 것을 의미
// Hilt Testing 에서의 Fake 객체를 주입하기 위해서
// 이런 방식은 테스트 환경에서만 적용되므로, 실제 애플리케이션의 동작에는 영향을 주지 않음
// 이를 통해 특정 부분의 로직만을 고립시켜 테스트할 수 있으며,
// 네트워크 요청이나 데이터베이스 접근 등의 부수적인 효과를 제거하여 테스트의 정확성과 안정성을 높일 수 있음
// 즉, 실제 앱 동작 환경과 독립적인 테스트 환경을 구성함으로써, 보다 정확하고 일관된 테스트 결과를 얻기 위함
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class VoiceToTextE2E {

    // 테스트 실행 전에 설정해야하는 규칙들
    // Compose UI Test
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    // Hit Test
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    // 오디오 녹음 권한 설정
    @get:Rule
    val permissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    @Inject
    lateinit var fakeVoiceParser: VoiceToTextParser

    @Inject
    lateinit var fakeClient: TranslateClient

    // Hilt 를 이용한 의존성 주입
    @Before
    fun setUp() {
        hiltRule.inject()
    }

    // 음성 녹음 -> 음성 녹음 중단 -> 번역
    // 각 동작 후에 예상되는 결과가 실제로 표기되는지 검증
    // 사용자 관점에서 테스트
    @Test
    fun recordAndTranslate() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceParser as FakeVoiceToTextParser
        val client = fakeClient as FakeTranslateClient

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.record_audio))
            .performClick()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.stop_recording))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription(context.getString(R.string.apply))
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule
            .onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule
            .onNodeWithText(client.translatedText)
            .assertIsDisplayed()
    }
}