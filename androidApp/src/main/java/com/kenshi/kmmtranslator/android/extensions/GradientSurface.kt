package com.kenshi.kmmtranslator.android.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// 화면의 모드에 따라(다크모드 인지 아닌지) Modifier 의 속성을 분기
// composed -> Modifier 참조를 제공하며, Modifier 를 반환해야 함
// custom modifier 를 만드는 방법!
fun Modifier.gradientSurface(): Modifier = composed {
    if (isSystemInDarkTheme()) {
        Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF23262E),
                    Color(0xFF212329),
                )
            )
        )
    } else Modifier.background(MaterialTheme.colors.surface)
}