package com.mathquest.app.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import com.mathquest.app.data.CHAPTERS
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.TextMid

@Composable
fun VictoryScreen(
    chapterIndex: Int,
    isLastChapter: Boolean,
    onNext: () -> Unit
) {
    val chapter = CHAPTERS[chapterIndex]

    val bgBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFFDE68A), Color(0xFFBBF7D0), Color(0xFFDBEAFE))
    )

    val infiniteTransition = rememberInfiniteTransition(label = "victoryBob")
    val artY by infiniteTransition.animateFloat(
        0f, -10f,
        infiniteRepeatable(tween(1500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "vY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Top confetti — emoji only block
        Text(
            text = "🎉🎊✨🌟💫",
            fontSize = 28.sp,
            letterSpacing = 4.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Monster/victory emoji — dedicated block
        Text(
            text = "🏆",
            fontSize = 88.sp,
            lineHeight = 100.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = artY.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Victory title — separate from emoji
        Text(
            text = "You defeated ${chapter.monsterName}!",
            fontFamily = Baloo2,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            color = Color(0xFF166534),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Subtitle
        Text(
            text = "${chapter.title} is saved! 🌟",
            fontFamily = Nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = TextMid,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mid confetti
        Text(
            text = "🎉🎊🎉🎊🎉",
            fontSize = 26.sp,
            letterSpacing = 4.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Next button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .height(56.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFF22C55E), Color(0xFF16A34A)))
                )
                .clickable { onNext() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isLastChapter) "🌀  Finish Quest!" else "Next Chapter →",
                fontFamily = Baloo2,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
