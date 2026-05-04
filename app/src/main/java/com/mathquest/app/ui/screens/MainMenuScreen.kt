package com.mathquest.app.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mathquest.app.R
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.TextDark

// Natural aspect ratios from the actual cropped PNG assets
private const val BUTTON_ASPECT = 538f / 121f      // ≈ 4.45 — main wide buttons
private const val ICON_ASPECT = 154f / 172f        // ≈ 0.90 — settings/profile

@Composable
fun MainMenuScreen(
    hasSavedProgress: Boolean,
    playerName: String,
    musicVolume: Float,
    sfxVolume: Float,
    onMusicVolume: (Float) -> Unit,
    onSfxVolume: (Float) -> Unit,
    onStartAdventure: () -> Unit,
    onContinue: () -> Unit,
    onViewProgress: () -> Unit
) {
    var showSettings by remember { mutableStateOf(false) }
    var showProfile by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        // Background — fills the entire landscape screen
        Image(
            painter = painterResource(id = R.drawable.bg_main_menu),
            contentDescription = "Main Menu Background",
            modifier = Modifier
                .fillMaxSize()
                .then(if (showSettings) Modifier.blur(14.dp) else Modifier),
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = !showSettings,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            MainMenuControls(
                hasSavedProgress = hasSavedProgress,
                onStartAdventure = onStartAdventure,
                onContinue = {
                    if (hasSavedProgress) onContinue()
                    else Toast.makeText(context, "No saved progress yet", Toast.LENGTH_SHORT).show()
                },
                onViewProgress = onViewProgress,
                onSettings = { showSettings = true },
                onProfile = { showProfile = true }
            )
        }

        if (showSettings) {
            SettingsOverlay(
                musicVolume = musicVolume,
                sfxVolume = sfxVolume,
                onMusicVolume = onMusicVolume,
                onSfxVolume = onSfxVolume,
                onClose = { showSettings = false }
            )
        }

        if (showProfile) {
            ProfileDialog(
                playerName = playerName,
                onClose = { showProfile = false }
            )
        }
    }
}

@Composable
private fun MainMenuControls(
    hasSavedProgress: Boolean,
    onStartAdventure: () -> Unit,
    onContinue: () -> Unit,
    onViewProgress: () -> Unit,
    onSettings: () -> Unit,
    onProfile: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        // Icon size based on screen height — roughly 12% of available height
        val iconSize = (maxHeight * 0.13f).coerceIn(54.dp, 86.dp)
        // Main button width — narrower so the "Quest" title stays visible behind
        val buttonWidth = (maxWidth * 0.20f).coerceIn(150.dp, 240.dp)

        // TOP-LEFT: Settings + Profile stacked vertically
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 12.dp, start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ImageIconButton(
                resId = R.drawable.btn_settings,
                description = "Settings",
                onClick = onSettings,
                size = iconSize
            )
            ImageIconButton(
                resId = R.drawable.btn_profile,
                description = "Profile",
                onClick = onProfile,
                size = iconSize
            )
        }

        // CENTER-BOTTOM: 3 wide buttons stacked vertically
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Start Adventure
            Image(
                painter = painterResource(id = R.drawable.btn_start),
                contentDescription = "Start Adventure",
                modifier = Modifier
                    .width(buttonWidth)
                    .aspectRatio(BUTTON_ASPECT)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onStartAdventure() },
                contentScale = ContentScale.Fit
            )

            // Continue (blurred when no saved progress)
            Image(
                painter = painterResource(id = R.drawable.btn_continue_game),
                contentDescription = "Continue",
                modifier = Modifier
                    .width(buttonWidth)
                    .aspectRatio(BUTTON_ASPECT)
                    .alpha(if (hasSavedProgress) 1f else 0.45f)
                    .then(if (!hasSavedProgress) Modifier.blur(3.dp) else Modifier)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onContinue() },
                contentScale = ContentScale.Fit
            )

            // View Progress
            Image(
                painter = painterResource(id = R.drawable.btn_view_progress),
                contentDescription = "View Progress",
                modifier = Modifier
                    .width(buttonWidth)
                    .aspectRatio(BUTTON_ASPECT)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onViewProgress() },
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun ImageButton(
    resId: Int,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alpha: Float = 1f
) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = description,
        modifier = modifier
            .alpha(alpha)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ImageIconButton(
    resId: Int,
    description: String,
    onClick: () -> Unit,
    size: androidx.compose.ui.unit.Dp
) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = description,
        modifier = Modifier
            .size(size)
            .aspectRatio(ICON_ASPECT)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun ProfileDialog(playerName: String, onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Profile",
                    fontFamily = Baloo2,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = TextDark
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = if (playerName.isBlank())
                        "No hero yet — tap Start Adventure"
                    else
                        "Hero: $playerName",
                    fontFamily = Nunito,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = TextDark,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFF7C3AED), RoundedCornerShape(999.dp))
                        .clickable { onClose() }
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text(
                        "Close",
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
