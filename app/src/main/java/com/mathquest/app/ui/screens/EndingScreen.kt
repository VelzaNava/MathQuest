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
import androidx.compose.foundation.layout.Row
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
import com.mathquest.app.GameState
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.BlueDark
import com.mathquest.app.ui.theme.Nunito

@Composable
fun EndingScreen(
    state: GameState,
    onPlayAgain: () -> Unit
) {
    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0C4A6E),
            Color(0xFF075985),
            Color(0xFF0369A1),
            Color(0xFF38BDF8)
        )
    )

    val infiniteTransition = rememberInfiniteTransition(label = "portalBob")
    val artY by infiniteTransition.animateFloat(
        0f, -12f,
        infiniteRepeatable(tween(1500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "pY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Title
        Text(
            text = "Quest Complete!",
            fontFamily = Baloo2,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = androidx.compose.ui.text.TextStyle(
                shadow = androidx.compose.ui.graphics.Shadow(
                    color = Color(0x663819A1),
                    blurRadius = 12f
                )
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "You saved Wumbo from the darkness!",
            fontFamily = Nunito,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Portal art — dedicated block
        Text(
            text = "🌀",
            fontSize = 96.sp,
            lineHeight = 110.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = artY.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Stats card — each line starts with emoji then text, no mixing
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            StatLine(emoji = "🧙", label = "Hero", value = state.playerName.ifBlank { "Brave One" })
            StatLine(emoji = "✅", label = "Problems solved", value = "${state.totalRight}")
            StatLine(emoji = "❤️", label = "Hearts remaining", value = "${state.hearts}")
            StatLine(emoji = "🏆", label = "Chapters completed", value = "${state.completedChapters.size} / 3")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Play again button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color.White)
                .clickable { onPlayAgain() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🎮  Play Again",
                fontFamily = Baloo2,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = BlueDark
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun StatLine(emoji: String, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Emoji — its own text element
        Text(text = emoji, fontSize = 20.sp, lineHeight = 24.sp)
        Spacer(modifier = Modifier.padding(start = 8.dp))
        // Label + value — separate
        Text(
            text = "$label: $value",
            fontFamily = Nunito,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color.White,
            lineHeight = 22.sp
        )
    }
}
