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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mathquest.app.R
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.TextDark
import com.mathquest.app.ui.theme.TextMid

private const val PANEL_ASPECT = 829f / 951f
private const val SETTINGS_HOME_ASPECT = 105f / 95f

@Composable
fun SettingsOverlay(
    musicVolume: Float,
    sfxVolume: Float,
    onMusicVolume: (Float) -> Unit,
    onSfxVolume: (Float) -> Unit,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f)),
            contentAlignment = Alignment.Center
        ) {
            val panelHeight = (maxHeight * 0.92f).coerceAtMost(560.dp)
            val panelWidth = (panelHeight * PANEL_ASPECT)
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
                // Paper note background — already contains "SETTINGS" title
                Image(
                    painter = painterResource(id = R.drawable.bg_settings),
                    contentDescription = "Settings Panel",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // Content overlay — INSIDE the paper interior, below baked-in title
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = panelWidth * 0.20f,
                            end = panelWidth * 0.10f,
                            top = panelHeight * 0.34f,    // below "SETTINGS" title
                            bottom = panelHeight * 0.10f
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Sliders block (top of interior)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(panelHeight * 0.025f)
                    ) {
                        CompactVolumeRow(
                            iconEmoji = "🎵",
                            label = "Music",
                            value = musicVolume,
                            onValueChange = onMusicVolume
                        )
                        CompactVolumeRow(
                            iconEmoji = "🔊",
                            label = "SFX",
                            value = sfxVolume,
                            onValueChange = onSfxVolume
                        )
                    }

                    Spacer(modifier = Modifier.height(panelHeight * 0.02f))

                    // Big Home button
                    Image(
                        painter = painterResource(id = R.drawable.btn_settings_home),
                        contentDescription = "Close Settings",
                        modifier = Modifier
                            .height((panelHeight * 0.13f).coerceIn(50.dp, 80.dp))
                            .aspectRatio(SETTINGS_HOME_ASPECT)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onClose() },
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

/** Compact slider row that fits inside narrow paper panel area. */
@Composable
fun CompactVolumeRow(
    iconEmoji: String,
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = iconEmoji, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = TextDark
                )
            }
            Text(
                text = "${(value * 100).toInt()}%",
                fontFamily = Nunito,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                color = TextMid
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF7C3AED),
                activeTrackColor = Color(0xFFA78BFA),
                inactiveTrackColor = Color(0xFFE9D5FF)
            )
        )
    }
}

/** Original taller volume row, kept for any non-paper contexts. */
@Composable
fun VolumeRow(
    iconEmoji: String,
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = iconEmoji, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextDark
                )
            }
            Text(
                text = "${(value * 100).toInt()}%",
                fontFamily = Nunito,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = TextMid
            )
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF7C3AED),
                activeTrackColor = Color(0xFFA78BFA),
                inactiveTrackColor = Color(0xFFE9D5FF)
            )
        )
    }
}
