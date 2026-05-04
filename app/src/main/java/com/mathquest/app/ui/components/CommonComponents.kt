package com.mathquest.app.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.ui.theme.Nunito

@Composable
fun BobAnimation(
    durationMs: Int = 2000,
    amplitude: Dp = 10.dp,
    content: @Composable (offsetY: Dp) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bob")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -amplitude.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMs / 2, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bobY"
    )
    content(offsetY.dp)
}

@Composable
fun WiggleAnimation(
    durationMs: Int = 1000,
    content: @Composable (rotation: Float) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wiggle")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMs / 2, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wiggleRot"
    )
    content(rotation)
}

@Composable
fun ShimmerAnimation(
    durationMs: Int = 1200,
    content: @Composable (alpha: Float) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMs / 2, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerAlpha"
    )
    content(alpha)
}

@Composable
fun EmojiDisplay(
    emoji: String,
    fontSize: Float = 48f,
    modifier: Modifier = Modifier
) {
    Text(
        text = emoji,
        fontSize = fontSize.sp,
        lineHeight = (fontSize * 1.2).sp,
        modifier = modifier
    )
}

@Composable
fun BobbingEmoji(
    emoji: String,
    fontSize: Float = 48f,
    durationMs: Int = 2000,
    amplitude: Dp = 8.dp,
    modifier: Modifier = Modifier
) {
    BobAnimation(durationMs = durationMs, amplitude = amplitude) { offsetY ->
        Text(
            text = emoji,
            fontSize = fontSize.sp,
            lineHeight = (fontSize * 1.2).sp,
            modifier = modifier.offset(y = offsetY)
        )
    }
}

@Composable
fun TopicBadge(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontFamily = Nunito,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = color
        )
    }
}
