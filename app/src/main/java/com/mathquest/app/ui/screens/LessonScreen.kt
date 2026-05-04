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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.mathquest.app.ui.theme.Cream
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.TextDark
import com.mathquest.app.ui.theme.TextMid

private val chapterHeaderBrushes = listOf(
    Brush.linearGradient(listOf(Color(0xFF16A34A), Color(0xFF4ADE80))),
    Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFFA78BFA))),
    Brush.linearGradient(listOf(Color(0xFF0369A1), Color(0xFF38BDF8)))
)

private val chapterBtnBrushes = listOf(
    Brush.linearGradient(listOf(Color(0xFF22C55E), Color(0xFF16A34A))),
    Brush.linearGradient(listOf(Color(0xFFA78BFA), Color(0xFF7C3AED))),
    Brush.linearGradient(listOf(Color(0xFF38BDF8), Color(0xFF0369A1)))
)

@Composable
fun LessonScreen(
    chapterIndex: Int,
    onBack: () -> Unit,
    onStartChallenge: () -> Unit
) {
    val chapter = CHAPTERS[chapterIndex]
    val lesson = chapter.lesson
    val headerBrush = chapterHeaderBrushes[chapterIndex]
    val btnBrush = chapterBtnBrushes[chapterIndex]

    val infiniteTransition = rememberInfiniteTransition(label = "lessonArt")
    val artY by infiniteTransition.animateFloat(
        0f, -10f,
        infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "artY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerBrush, RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .padding(top = 44.dp, bottom = 24.dp)
        ) {
            // Back button
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f))
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text("←", fontSize = 20.sp, color = Color.White, fontFamily = Baloo2, fontWeight = FontWeight.Bold)
            }

            // Art emoji — dedicated block
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = chapter.emoji,
                    fontSize = 88.sp,
                    lineHeight = 100.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.offset(y = artY.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = lesson.title,
                    fontFamily = Baloo2,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 60.dp)
                )
            }
        }

        // Scrollable content
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            // Main lesson text card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Text(
                    text = lesson.body,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = TextDark,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Example cards
            lesson.examples.forEachIndexed { i, example ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Example label
                    Text(
                        text = "EXAMPLE: ${example.label}",
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = TextMid,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Visual — emoji only, dedicated block
                    Text(
                        text = example.visual,
                        fontSize = 22.sp,
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Equation — no emoji, dedicated block
                    Box(
                        modifier = Modifier
                            .background(Cream, RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = example.equation,
                            fontFamily = Baloo2,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = TextDark,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                if (i < lesson.examples.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        // Fight button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(btnBrush)
                .clickable { onStartChallenge() }
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "⚔️  Let's Fight!",
                fontFamily = Baloo2,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}
