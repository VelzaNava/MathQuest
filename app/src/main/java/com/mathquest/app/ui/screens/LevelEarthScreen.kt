package com.mathquest.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.R
import com.mathquest.app.ui.theme.Baloo2
import kotlinx.coroutines.delay

// Pause icon natural ratio (cropped)
private const val PAUSE_ICON_ASPECT = 173f / 155f

@Composable
fun LevelEarthScreen(
    playerName: String,
    musicVolume: Float,
    sfxVolume: Float,
    onMusicVolume: (Float) -> Unit,
    onSfxVolume: (Float) -> Unit,
    onHome: () -> Unit
) {
    var monsterVisible by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }
    var restartKey by remember { mutableStateOf(0) }

    // Restart toggles this key to re-trigger LaunchedEffect
    LaunchedEffect(restartKey) {
        monsterVisible = false
        delay(450)
        monsterVisible = true
    }

    val monsterAlpha by animateFloatAsState(
        targetValue = if (monsterVisible) 1f else 0f,
        animationSpec = tween(900, easing = FastOutSlowInEasing),
        label = "monsterAlpha"
    )
    val monsterScale by animateFloatAsState(
        targetValue = if (monsterVisible) 1f else 0.65f,
        animationSpec = tween(900, easing = FastOutSlowInEasing),
        label = "monsterScale"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "monsterBob")
    val bobY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -16f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bobY"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Level — blurred while paused
        Box(
            modifier = Modifier
                .fillMaxSize()
                .then(if (isPaused) Modifier.blur(18.dp) else Modifier)
        ) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.bg_earth),
                contentDescription = "Earth Level",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Monster — bigger and lower than before
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.monster_multiplication_default),
                    contentDescription = "Multiplication Monster",
                    modifier = Modifier
                        .fillMaxWidth(0.78f)
                        .fillMaxHeight(0.92f)
                        .padding(bottom = 8.dp)
                        .alpha(monsterAlpha)
                        .offset(y = bobY.dp)
                        .graphicsLayer(scaleX = monsterScale, scaleY = monsterScale),
                    contentScale = ContentScale.Fit
                )
            }

            // Top-left: hero name pill
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 18.dp, start = 14.dp)
                    .background(Color(0xCC1E1B4B), RoundedCornerShape(999.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "🧙 ${playerName.ifBlank { "Hero" }}",
                    fontFamily = Baloo2,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }

            // Top-right: pause button
            Image(
                painter = painterResource(id = R.drawable.btn_pause),
                contentDescription = "Pause",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 14.dp, end = 14.dp)
                    .height(64.dp)
                    .aspectRatio(PAUSE_ICON_ASPECT)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { isPaused = true },
                contentScale = ContentScale.Fit
            )
        }

        // Pause overlay (on top of blurred level)
        AnimatedVisibility(
            visible = isPaused,
            enter = fadeIn(tween(220)),
            exit = fadeOut(tween(180))
        ) {
            PauseOverlay(
                musicVolume = musicVolume,
                sfxVolume = sfxVolume,
                onMusicVolume = onMusicVolume,
                onSfxVolume = onSfxVolume,
                onResume = { isPaused = false },
                onRestart = {
                    isPaused = false
                    restartKey++ // re-trigger entry animation
                },
                onHome = onHome
            )
        }
    }
}
