package com.kenshi.kmmtranslator.android.voice_to_text.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kenshi.kmmtranslator.android.TranslatorTheme
import com.kenshi.kmmtranslator.android.extensions.gradientSurface
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun VoiceRecorderDisplay(
    powerRatios: List<Float>,
    modifier: Modifier = Modifier,
) {
    val primary = MaterialTheme.colors.primary

    Box(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .gradientSurface()
            .padding(
                horizontal = 32.dp,
                vertical = 8.dp
            )
            // 캔버스가 만들어짐
            .drawBehind {
                val powerRatioWith = 3.dp.toPx()
                val powerRatioCount = (size.width / (2 * powerRatioWith)).toInt()

                // 경계를 넘어 그려지지 않도록, 경계를 설정
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height
                ) {
                    powerRatios
                        // 가장 최신값을 취함
                        .takeLast(powerRatioCount)
                        .reversed()
                        // 원점(0, 0) 이 왼쪽 상단이고 x 값이 증가할 수록 오른쪽, y값이 증가할 수록 아래로 내려감
                        // 따라서 - (size.height / 2f) * ratio 해준게 최상단
                        .forEachIndexed { i, ratio ->
                            val yTopStart = center.y - (size.height / 2f) * ratio
                            // 그래프가 같은 위치에 계속 그려지지 않도록 위치를 옮김
                            drawRoundRect(
                                color = primary,
                                topLeft = Offset(
                                    x = size.width - i * 2 * powerRatioWith,
                                    y = yTopStart
                                ),
                                size = Size(
                                    width = powerRatioWith,
                                    height = (center.y - yTopStart) * 2f
                                ),
                                // float 으로 설정하면 퍼센트가 됨
                                cornerRadius = CornerRadius(100f),
                            )
                        }
                }
            }
    )
}

@Preview
@Composable
fun VoiceRecorderDisplayPreview() {
    TranslatorTheme {
        VoiceRecorderDisplay(
            powerRatios = (0..50).map {
                val percent = it / 100f
                // 사인파의 주기를 구성
                sin(percent * 2 * PI).toFloat()
                // Random.nextFloat()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
    }
}