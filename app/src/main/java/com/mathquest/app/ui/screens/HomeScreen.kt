package com.mathquest.app.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Cream
import com.mathquest.app.ui.theme.GreenDark
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.Pink
import com.mathquest.app.ui.theme.PinkDark
import com.mathquest.app.ui.theme.Purple
import com.mathquest.app.ui.theme.PurpleLight
import com.mathquest.app.ui.theme.TextDark

@Composable
fun HomeScreen(
    hasSave: Boolean,
    onPlay: () -> Unit,
    onContinue: () -> Unit
) {
    val bgBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFA78BFA),
            Color(0xFFC4B5FD),
            Color(0xFFFBCFE8),
            Color(0xFFFDE68A)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) {
        // Background decorations (clouds + sparkles)
        HomeBgDecorations()

        // Ground hills
        HomeGround()

        // Characters row near bottom
        HomeCharactersRow()

        // Main content column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Animated book hero
            BookHero()

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = "MathQuest",
                fontFamily = Baloo2,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 52.sp,
                color = Color.White,
                style = androidx.compose.ui.text.TextStyle(
                    shadow = androidx.compose.ui.graphics.Shadow(
                        color = Color(0x66000000),
                        offset = Offset(0f, 4f),
                        blurRadius = 16f
                    )
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "✨ A magical math adventure ✨",
                fontFamily = Nunito,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Play button
            PlayButton(onClick = onPlay)

            if (hasSave) {
                Spacer(modifier = Modifier.height(12.dp))
                ContinueButton(onClick = onContinue)
            }
        }
    }
}

@Composable
private fun BookHero() {
    val infiniteTransition = rememberInfiniteTransition(label = "bookBob")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bookY"
    )

    Canvas(
        modifier = Modifier
            .size(180.dp, 130.dp)
            .offset(y = offsetY.dp)
    ) {
        val w = size.width
        val h = size.height
        val spineX = w / 2f
        val bookTop = 10f
        val bookBottom = h - 10f
        val bookLeft = 10f
        val bookRight = w - 10f

        // Left page (cream)
        drawRoundRect(
            color = Color(0xFFFFF8EE),
            topLeft = Offset(bookLeft, bookTop),
            size = Size(spineX - bookLeft - 4f, bookBottom - bookTop),
            cornerRadius = CornerRadius(8f, 8f)
        )

        // Right page (yellow)
        drawRoundRect(
            color = Color(0xFFFDE68A),
            topLeft = Offset(spineX + 4f, bookTop),
            size = Size(bookRight - spineX - 4f, bookBottom - bookTop),
            cornerRadius = CornerRadius(8f, 8f)
        )

        // Spine
        drawRoundRect(
            color = Purple,
            topLeft = Offset(spineX - 5f, bookTop - 4f),
            size = Size(10f, bookBottom - bookTop + 8f),
            cornerRadius = CornerRadius(5f, 5f)
        )

        // Book outline
        drawRoundRect(
            color = Color(0xFFD8B4FE),
            topLeft = Offset(bookLeft, bookTop),
            size = Size(bookRight - bookLeft, bookBottom - bookTop),
            cornerRadius = CornerRadius(8f, 8f),
            style = Stroke(width = 2f)
        )

        // Left page lines
        val lineColor = Color(0xFFD8B4FE)
        val lineY1 = bookTop + (bookBottom - bookTop) * 0.35f
        val lineY2 = bookTop + (bookBottom - bookTop) * 0.55f
        val lineY3 = bookTop + (bookBottom - bookTop) * 0.75f
        val lx1 = bookLeft + 12f
        val lx2 = spineX - 14f
        drawLine(lineColor, Offset(lx1, lineY1), Offset(lx2, lineY1), strokeWidth = 1.5f)
        drawLine(lineColor, Offset(lx1, lineY2), Offset(lx2, lineY2), strokeWidth = 1.5f)
        drawLine(lineColor, Offset(lx1, lineY3), Offset(lx2, lineY3), strokeWidth = 1.5f)

        // Stars around book
        val starPositions = listOf(
            Offset(bookLeft - 8f, bookTop + 10f),
            Offset(bookRight + 8f, bookTop + 15f),
            Offset(bookLeft - 12f, bookBottom - 20f),
            Offset(bookRight + 10f, bookBottom - 15f)
        )
        starPositions.forEach { pos ->
            drawCircle(color = Color(0xFFFDE68A), radius = 5f, center = pos)
            drawCircle(color = Color(0xFFFCD34D), radius = 3f, center = pos)
        }
    }
}

@Composable
private fun HomeBgDecorations() {
    val infiniteTransition = rememberInfiniteTransition(label = "clouds")
    val cloud1X by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 450f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "c1"
    )
    val cloud2X by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 450f,
        animationSpec = infiniteRepeatable(
            animation = tween(18000, easing = LinearEasing, delayMillis = 4000),
            repeatMode = RepeatMode.Restart
        ),
        label = "c2"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "☁️",
            fontSize = 36.sp,
            modifier = Modifier
                .offset(x = cloud1X.dp, y = 40.dp)
        )
        Text(
            text = "☁️",
            fontSize = 24.sp,
            modifier = Modifier
                .offset(x = cloud2X.dp, y = 80.dp)
        )

        // Static sparkle dots
        listOf(
            Triple(20f, 120f, 10f),
            Triple(340f, 90f, 8f),
            Triple(50f, 300f, 6f),
            Triple(310f, 250f, 10f),
            Triple(180f, 60f, 7f),
        ).forEach { (x, y, r) ->
            Canvas(
                modifier = Modifier
                    .offset(x = x.dp, y = y.dp)
                    .size((r * 2).dp)
            ) {
                drawCircle(Color(0xFFFDE68A).copy(alpha = 0.6f), radius = r)
            }
        }
    }
}

@Composable
private fun HomeGround() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 0.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
        ) {
            val w = size.width
            val h = size.height

            val path = Path().apply {
                moveTo(0f, h * 0.4f)
                cubicTo(w * 0.15f, h * 0.1f, w * 0.35f, h * 0.6f, w * 0.5f, h * 0.3f)
                cubicTo(w * 0.65f, 0f, w * 0.85f, h * 0.5f, w, h * 0.25f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(path, color = GreenDark)

            val path2 = Path().apply {
                moveTo(0f, h * 0.55f)
                cubicTo(w * 0.2f, h * 0.3f, w * 0.4f, h * 0.7f, w * 0.6f, h * 0.45f)
                cubicTo(w * 0.75f, h * 0.25f, w * 0.9f, h * 0.6f, w, h * 0.4f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(path2, color = Color(0xFF22C55E))
        }
    }
}

@Composable
private fun HomeCharactersRow() {
    val infiniteTransition = rememberInfiniteTransition(label = "chars")
    val char1Y by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -8f,
        animationSpec = infiniteRepeatable(tween(1800, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "c1"
    )
    val char2Y by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -8f,
        animationSpec = infiniteRepeatable(tween(2200, easing = FastOutSlowInEasing, delayMillis = 400), RepeatMode.Reverse),
        label = "c2"
    )
    val char3Y by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = -8f,
        animationSpec = infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing, delayMillis = 800), RepeatMode.Reverse),
        label = "c3"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 18.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "🦄", fontSize = 40.sp, modifier = Modifier.offset(y = char1Y.dp))
            Text(text = "🧙", fontSize = 44.sp, modifier = Modifier.offset(y = char2Y.dp))
            Text(text = "🐉", fontSize = 38.sp, modifier = Modifier.offset(y = char3Y.dp))
        }
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "playPulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.03f,
        animationSpec = infiniteRepeatable(tween(900, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "ps"
    )

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .shadow(8.dp, RoundedCornerShape(999.dp)),
        shape = RoundedCornerShape(999.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(listOf(Pink, Color(0xFFFF8C7A))),
                    RoundedCornerShape(999.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "▶  Play Now",
                fontFamily = Baloo2,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ContinueButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(999.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.25f)),
        border = androidx.compose.foundation.BorderStroke(2.dp, Color.White.copy(alpha = 0.6f))
    ) {
        Text(
            text = "📚  Continue",
            fontFamily = Baloo2,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}
