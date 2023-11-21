package com.kenshi.kmmtranslator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kenshi.kmmtranslator.android.core.presentation.Routes
import com.kenshi.kmmtranslator.android.translate.presentation.AndroidTranslateViewModel
import com.kenshi.kmmtranslator.android.translate.presentation.TranslateScreen
import com.kenshi.kmmtranslator.android.voice_to_text.presentation.AndroidVoiceToTextViewModel
import com.kenshi.kmmtranslator.android.voice_to_text.presentation.VoiceToTextScreen
import com.kenshi.kmmtranslator.translate.presentation.TranslateEvent
import com.kenshi.kmmtranslator.voice_to_text.presentation.VoiceToTextEvent
import dagger.hilt.android.AndroidEntryPoint

// 참고) 음성 인식 API 가 음성 인식이 아직 완료되지 않은 상태에서 듣기 중지를 호출하면 에러 발생
// Error: 5 (클라이언트 에러를 의미)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TranslatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TranslateRoot()
                }
            }
        }
    }
}

@Composable
fun TranslateRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.TRANSLATE,
    ) {
        composable(route = Routes.TRANSLATE) { navBackStackEntry ->
            val viewModel = hiltViewModel<AndroidTranslateViewModel>()
            // collectAsState() 여도 되는 이유? collectAsStateWithLifecycle() 을 안해도 되는 이유
            // route 역할을 하는 composable 이기 때문에?
            val state by viewModel.state.collectAsState()

            val voiceResult by navBackStackEntry
                // navBackStackEntry 클래스 내에 포함 되어있음
                .savedStateHandle
                .getStateFlow<String?>("voiceResult", null)
                .collectAsState()

            LaunchedEffect(voiceResult) {
                viewModel.onEvent(TranslateEvent.SubmitVoiceResult(voiceResult))
                // VoiceResult 를 제출한 이후 이를 제거(reset)
                navBackStackEntry.savedStateHandle["voiceResult"] = null
            }

            //필요한 속성들만 viewModel 에서 꺼내 Screen 으로 넘기는 것을 지향
            TranslateScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is TranslateEvent.RecordAudio -> {
                            navController.navigate(
                                Routes.VOICE_TO_TEXT + "/${state.fromLanguage.language.langCode}"
                            )
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        composable(
            route = Routes.VOICE_TO_TEXT + "/{languageCode}",
            arguments = listOf(
                navArgument("languageCode") {
                    type = NavType.StringType
                    defaultValue = "en"
                }
            )
        ) { navBackStackEntry ->
            val languageCode = navBackStackEntry.arguments?.getString("languageCode") ?: "en"
            val viewModel = hiltViewModel<AndroidVoiceToTextViewModel>()
            val state by viewModel.state.collectAsState()

            VoiceToTextScreen(
                state = state,
                languageCode = languageCode,
                onResult = { spokenText ->
                    // navigation 을 이용한 onActivityResult 와 같은 방법
                    navController.previousBackStackEntry?.savedStateHandle?.set(
                        "voiceResult", spokenText
                    )
                    navController.popBackStack()
                },
                onEvent = { event ->
                    when (event) {
                        is VoiceToTextEvent.Close -> {
                            navController.popBackStack()
                        }
                        else -> viewModel.onEvent(event)
                    }
                },
            )
        }
    }
}