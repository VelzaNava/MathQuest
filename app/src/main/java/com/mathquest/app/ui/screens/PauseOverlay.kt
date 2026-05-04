package com.mathquest.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mathquest.app.R

private const val PAUSE_PANEL_ASPECT = 829f / 951f
private const val PAUSE_BTN_ASPECT = 105f / 95f

@Composable
fun PauseOverlay(
    musicVolume: Float,
    sfxVolume: Float,
    onMusicVolume: (Float) -> Unit,
    onSfxVolume: (Float) -> Unit,
    onResume: () -> Unit,
    onRestart: () -> Unit,
    onHome: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { /* block clicks-through */ },
        contentAlignment = Alignment.Center
    ) {
        // Center panel
        val panelHeight = (maxHeight * 0.95f).coerceAtMost(620.dp)
        val panelWidth = (panelHeight * PAUSE_PANEL_ASPECT)
            .coerceAtMost(maxWidth * 0.62f)

        Box(
            modifier = Modifier
                .width(panelWidth)
                .height(panelHeight)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { /* swallow */ }
        ) {
            // Paper note background — already contains "PAUSED" title
            Image(
                painter = painterResource(id = R.drawable.bg_paused),
                contentDescription = "Pause Panel",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // Content overlay — kept INSIDE the paper interior
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = panelWidth * 0.20f,    // past the red margin line
                        end = panelWidth * 0.10f,
                        top = panelHeight * 0.34f,     // below the "PAUSED" baked-in title
                        bottom = panelHeight * 0.10f
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Sliders block
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(panelHeight * 0.025f)
                ) {
                    CompactVolumeRow(
                        iconEmoji = "🔊",
                        label = "SFX",
                        value = sfxVolume,
                        onValueChange = onSfxVolume
                    )
                    CompactVolumeRow(
                        iconEmoji = "🎵",
                        label = "Music",
                        value = musicVolume,
                        onValueChange = onMusicVolume
                    )
                }

                Spacer(modifier = Modifier.height(panelHeight * 0.02f))

                // Bottom row: home, resume, restart
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val btnHeight = (panelHeight * 0.11f).coerceIn(46.dp, 72.dp)
                    PauseIconButton(R.drawable.btn_paused_home, "Home", btnHeight, onHome)
                    PauseIconButton(R.drawable.btn_resume, "Resume", btnHeight, onResume)
                    PauseIconButton(R.drawable.btn_restart, "Restart", btnHeight, onRestart)
                }
            }
        }
    }
}

@Composable
private fun PauseIconButton(
    resId: Int,
    description: String,
    btnHeight: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = description,
        modifier = Modifier
            .height(btnHeight)
            .aspectRatio(PAUSE_BTN_ASPECT)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        contentScale = ContentScale.Fit
    )
}
