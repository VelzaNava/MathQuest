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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.data.CHAPTERS
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Ch1Border
import com.mathquest.app.ui.theme.Ch2Border
import com.mathquest.app.ui.theme.Ch3Border
import com.mathquest.app.ui.theme.GreenDark
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.Purple
import com.mathquest.app.ui.theme.TextDark
import com.mathquest.app.ui.theme.TextMid

private val chapterBgs = listOf(
    Brush.linearGradient(listOf(Color(0xFFDCFCE7), Color(0xFFBBF7D0))),
    Brush.linearGradient(listOf(Color(0xFFEDE9FE), Color(0xFFDDD6FE))),
    Brush.linearGradient(listOf(Color(0xFFDBEAFE), Color(0xFFBFDBFE)))
)

private val chapterHeaderBgs = listOf(
    Brush.linearGradient(listOf(Color(0xFF16A34A), Color(0xFF4ADE80))),
    Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFFA78BFA))),
    Brush.linearGradient(listOf(Color(0xFF0369A1), Color(0xFF38BDF8)))
)

private val chapterBorders = listOf(Ch1Border, Ch2Border, Ch3Border)

private val chapterBtnColors = listOf(
    Brush.linearGradient(listOf(Color(0xFF22C55E), Color(0xFF16A34A))),
    Brush.linearGradient(listOf(Color(0xFFA78BFA), Color(0xFF7C3AED))),
    Brush.linearGradient(listOf(Color(0xFF38BDF8), Color(0xFF0369A1)))
)

@Composable
fun ChaptersScreen(
    playerName: String,
    completedChapters: Set<Int>,
    onSelectChapter: (Int) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "avatarBob")
    val avatarY by infiniteTransition.animateFloat(
        0f, -8f,
        infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "av"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F0FF))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(listOf(Color(0xFF7C3AED), Color(0xFFA78BFA))),
                    RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(top = 48.dp, start = 24.dp, end = 90.dp, bottom = 24.dp)
        ) {
            Column {
                Text(
                    text = "Choose a Quest",
                    fontFamily = Baloo2,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Go, $playerName! 🌟",
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
            }

            // Avatar emoji — positioned at right, enough offset to not overlap text
            Text(
                text = "🧙",
                fontSize = 52.sp,
                lineHeight = 60.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = (-66).dp) // aligns to the right of the Box
                    .offset(y = avatarY.dp)
            )
        }

        // Chapter cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            CHAPTERS.forEachIndexed { index, chapter ->
                val isCompleted = index in completedChapters
                val isLocked = index > 0 && (index - 1) !in completedChapters

                ChapterCard(
                    chapterIndex = index,
                    title = chapter.title,
                    topic = chapter.topic,
                    emoji = chapter.emoji,
                    isCompleted = isCompleted,
                    isLocked = isLocked,
                    bgBrush = chapterBgs[index],
                    borderColor = chapterBorders[index],
                    buttonBrush = chapterBtnColors[index],
                    onClick = { if (!isLocked) onSelectChapter(index) }
                )

                if (index < CHAPTERS.size - 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ChapterCard(
    chapterIndex: Int,
    title: String,
    topic: String,
    emoji: String,
    isCompleted: Boolean,
    isLocked: Boolean,
    bgBrush: Brush,
    borderColor: Color,
    buttonBrush: Brush,
    onClick: () -> Unit
) {
    val starsText = if (isCompleted) "⭐⭐⭐" else if (isLocked) "🔒🔒🔒" else "☆☆☆"
    val alpha = if (isLocked) 0.5f else 1f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(bgBrush)
            .border(2.dp, borderColor, RoundedCornerShape(24.dp))
            .clickable(enabled = !isLocked) { onClick() }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top row: emoji + info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Chapter emoji icon — dedicated block, flex-shrink:0
                Box(
                    modifier = Modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isLocked) "🔒" else emoji,
                        fontSize = 40.sp,
                        lineHeight = 48.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Text info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Chapter ${chapterIndex + 1}",
                        fontFamily = Nunito,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = TextMid,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = title,
                        fontFamily = Baloo2,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = if (isLocked) TextMid else TextDark
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    // Topic badge
                    Box(
                        modifier = Modifier
                            .background(
                                if (isLocked) Color.Gray.copy(alpha = 0.15f)
                                else borderColor.copy(alpha = 0.25f),
                                RoundedCornerShape(999.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = topic,
                            fontFamily = Nunito,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (isLocked) TextMid else TextDark
                        )
                    }
                }

                // Completed badge
                if (isCompleted) {
                    Text(text = "✅", fontSize = 22.sp)
                }
            }

            // Stars row — separate from text
            Row(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            ) {
                Text(text = starsText, fontSize = 18.sp)
            }

            // CTA button
            if (!isLocked) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(buttonBrush)
                        .clickable { onClick() }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isCompleted) "▶  Play Again" else "▶  Start Quest",
                        fontFamily = Baloo2,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
