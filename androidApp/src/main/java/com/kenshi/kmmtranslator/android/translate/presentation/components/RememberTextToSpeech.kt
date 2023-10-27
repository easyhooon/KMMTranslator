package com.kenshi.kmmtranslator.android.translate.presentation.components

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

// 컴포저블 함수도 함수이므로 리턴값을 가질 수 있다!
@Composable
fun rememberTextToSpeech(): TextToSpeech {
    val context = LocalContext.current
    val tts = remember { TextToSpeech(context, null) }
    // 객체를 쉽게 정리 하기 위한
    DisposableEffect(key1 = tts) {
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }
    return tts
}