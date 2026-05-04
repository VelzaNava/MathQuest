package com.mathquest.app.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.R
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.TextDark
import com.mathquest.app.ui.theme.TextMid
import kotlinx.coroutines.delay

private const val CONTINUE_BTN_ASPECT = 539f / 123f

@Composable
fun IntroStoryScreen(
    playerName: String,
    onContinue: () -> Unit
) {
    var phase by remember { mutableStateOf(0) }
    val whiteAlpha by animateFloatAsState(
        targetValue = if (phase >= 1) 1f else 0f,
        animationSpec = tween(1500, easing = LinearEasing),
        label = "whiteFade"
    )
    val contentAlpha by animateFloatAsState(
        targetValue = if (phase >= 2) 1f else 0f,
        animationSpec = tween(1800, easing = LinearEasing),
        label = "contentFade"
    )
    val buttonAlpha by animateFloatAsState(
        targetValue = if (phase >= 3) 1f else 0f,
        animationSpec = tween(900, easing = LinearEasing),
        label = "btnFade"
    )

    LaunchedEffect(Unit) {
        delay(150)
        phase = 1
        delay(1700)
        phase = 2
        delay(1900)
        phase = 3
    }

    val displayName = playerName.ifBlank { "Hero" }

    Box(modifier = Modifier.fillMaxSize()) {
        // Layer 1: dark starting backdrop
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1B4B))
        )

        // Layer 2: cream parchment/sky gradient that fades in (the "fade-to-white" transition)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(whiteAlpha)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFF8EE),  // warm cream top
                            Color(0xFFFFF3DB),  // mid
                            Color(0xFFFCE7C8)   // soft amber bottom
                        )
                    )
                )
        )

        // Layer 3: story content + button, both faded in over the parchment
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            // Capture constraints into locals so nested composables can use them
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val isLandscape = screenWidth > screenHeight
            val contentMaxWidth = if (isLandscape) (screenWidth * 0.78f).coerceAtMost(720.dp) else screenWidth
            val sidePadding = if (isLandscape) 32.dp else 22.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(contentAlpha),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Scrollable story area — flexes to fill remaining space, button stays pinned at bottom
                Column(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = sidePadding, vertical = 18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // Title
                    Text(
                        text = "Your story begins…",
                        fontFamily = Baloo2,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = TextDark,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.widthIn(max = contentMaxWidth)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Decorative divider
                    Text(
                        text = "✦  ✦  ✦",
                        fontFamily = Nunito,
                        fontSize = 12.sp,
                        color = Color(0xFF9F7AEA),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Story paragraphs — italic narration, with the hero's name highlighted
                    StoryParagraph(
                        text = "Long ago in the kingdom of Wumbo, numbers floated freely through the air. Equations sang in the rivers. Fractions glittered on morning dew. Children grew wise simply by listening to them.",
                        contentMaxWidth = contentMaxWidth
                    )

                    StoryParagraph(
                        text = "Then one cursed night, three monsters slipped through a tear in the sky. They drank the numbers from the world.",
                        contentMaxWidth = contentMaxWidth
                    )

                    StoryParagraph(
                        text = "Multiplication vanished from the forests. Division dried up the rivers. Fractions crumbled in the mountains. Without numbers, the kingdom began to fade.",
                        contentMaxWidth = contentMaxWidth
                    )

                    StoryParagraph(
                        text = "Far away, a dusty book on a shelf in your room creaked open by itself. The pages glowed. A voice whispered your name…",
                        contentMaxWidth = contentMaxWidth
                    )

                    // Highlighted name line
                    val nameLine = buildAnnotatedString {
                        append("The book has chosen you, ")
                        withStyle(
                            SpanStyle(
                                color = Color(0xFF7C3AED),
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = Baloo2,
                                fontSize = 18.sp
                            )
                        ) { append(displayName) }
                        append(".")
                    }
                    Text(
                        text = nameLine,
                        fontFamily = Nunito,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = TextDark,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp,
                        modifier = Modifier
                            .widthIn(max = contentMaxWidth)
                            .padding(vertical = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Call-to-arms line
                    Text(
                        text = "Restore the numbers. Defeat the monsters.\nSave Wumbo.",
                        fontFamily = Baloo2,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp,
                        color = Color(0xFF5B21B6),
                        textAlign = TextAlign.Center,
                        lineHeight = 26.sp,
                        modifier = Modifier.widthIn(max = contentMaxWidth)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Continue button — pinned at bottom, never cut off
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp, top = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val btnWidth = (screenWidth * 0.32f).coerceIn(220.dp, 360.dp)
                    Image(
                        painter = painterResource(id = R.drawable.btn_continue_story),
                        contentDescription = "Continue",
                        modifier = Modifier
                            .width(btnWidth)
                            .aspectRatio(CONTINUE_BTN_ASPECT)
                            .alpha(buttonAlpha)
                            .clickable(
                                enabled = phase >= 3,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onContinue() },
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
private fun StoryParagraph(
    text: String,
    contentMaxWidth: androidx.compose.ui.unit.Dp
) {
    Text(
        text = text,
        fontFamily = Nunito,
        fontWeight = FontWeight.SemiBold,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
        color = TextMid,
        lineHeight = 22.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .widthIn(max = contentMaxWidth)
            .padding(vertical = 8.dp)
    )
}
