package com.mathquest.app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.data.CUTSCENES
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Nunito

@Composable
fun CutsceneScreen(
    cutStep: Int,
    playerName: String,
    onNext: () -> Unit
) {
    val bgBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF1E1B4B), Color(0xFF3730A3), Color(0xFF4338CA))
    )

    val panel = CUTSCENES[cutStep]
    val isLast = cutStep == CUTSCENES.size - 1

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) {
        // Floating stars in bg
        CutsceneBgStars()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(
                targetState = cutStep,
                transitionSpec = {
                    (scaleIn(tween(400)) + fadeIn(tween(400))) togetherWith fadeOut(tween(200))
                },
                label = "cutscene"
            ) { step ->
                val currentPanel = CUTSCENES[step]
                CutscenePanel(
                    panel = currentPanel,
                    playerName = playerName
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Continue / Start button
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(listOf(Color(0xFFFCD34D), Color(0xFFFBBF24))),
                            RoundedCornerShape(999.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isLast) "⚔️  Start Quest!" else "Continue →",
                        fontFamily = Baloo2,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(CUTSCENES.size) { i ->
                    Box(
                        modifier = Modifier
                            .size(if (i == cutStep) 12.dp else 8.dp)
                            .background(
                                if (i == cutStep) Color(0xFFFCD34D) else Color.White.copy(alpha = 0.3f),
                                CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun CutscenePanel(
    panel: com.mathquest.app.data.CutscenePanel,
    playerName: String
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cutArt")
    val artY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            tween(2000, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "artY"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(28.dp))
            .border(2.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(28.dp))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Art emoji — separate block
        Text(
            text = panel.art,
            fontSize = 80.sp,
            lineHeight = 96.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = artY.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Text — separate block, no emoji mixed in
        val displayText = panel.text.replace("{playerName}", playerName)
        Text(
            text = displayText,
            fontFamily = Nunito,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.9f),
            lineHeight = 26.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CutsceneBgStars() {
    val infiniteTransition = rememberInfiniteTransition(label = "bgStars")
    val alpha by infiniteTransition.animateFloat(
        0.3f, 0.8f,
        infiniteRepeatable(tween(1500, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "starAlpha"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        listOf(
            Triple(30f, 80f, 14f),
            Triple(340f, 50f, 10f),
            Triple(60f, 200f, 8f),
            Triple(310f, 180f, 12f),
            Triple(180f, 40f, 9f),
            Triple(100f, 650f, 11f),
            Triple(270f, 620f, 8f),
        ).forEachIndexed { i, (x, y, size) ->
            Text(
                text = "✦",
                fontSize = size.sp,
                color = Color.White.copy(alpha = alpha * (if (i % 2 == 0) 1f else 0.7f)),
                modifier = Modifier.offset(x = x.dp, y = y.dp)
            )
        }
    }
}
