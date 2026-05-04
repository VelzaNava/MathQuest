package com.mathquest.app.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.WrongRed

@Composable
fun GameOverScreen(
    onRetry: () -> Unit,
    onReviewLesson: () -> Unit
) {
    val bgBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFFEE2E2), Color(0xFFFECACA), Color(0xFFFFF1F1))
    )

    val infiniteTransition = rememberInfiniteTransition(label = "goBob")
    val artY by infiniteTransition.animateFloat(
        0f, -8f,
        infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "goY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Art emoji — dedicated block
        Text(
            text = "😵",
            fontSize = 80.sp,
            lineHeight = 90.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = artY.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Title — separate from emoji
        Text(
            text = "Oh no! You fainted!",
            fontFamily = Baloo2,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 32.sp,
            color = Color(0xFF991B1B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle — no emoji
        Text(
            text = "The monster was too strong this time.\nBut heroes never give up!",
            fontFamily = Nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color(0xFFEF4444),
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Broken hearts — emoji only, no text adjacent
        Text(
            text = "💔  💔  💔",
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Retry button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFFEF4444), Color(0xFFDC2626)))
                )
                .clickable { onRetry() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "💪  Try Again!",
                fontFamily = Baloo2,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Review lesson button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color.White)
                .border(2.dp, WrongRed, RoundedCornerShape(999.dp))
                .clickable { onReviewLesson() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📖  Review Lesson",
                fontFamily = Baloo2,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = WrongRed
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
