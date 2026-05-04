package com.mathquest.app.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.Purple
import com.mathquest.app.ui.theme.TextDark
import com.mathquest.app.ui.theme.TextMid
import com.mathquest.app.ui.theme.Yellow

@Composable
fun NameEntryScreen(
    onConfirm: (String) -> Unit,
    onBack: () -> Unit
) {
    var nameInput by remember { mutableStateOf("") }

    val bgBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFFFDE68A), Color(0xFFFFF3C4), Color(0xFFFFF8EE))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgBrush)
    ) {
        // Floating bg emoji decorations (z-index behind card)
        BgDecoEmoji()

        // Center card
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            // Card with floating book emoji on top
            Box(contentAlignment = Alignment.TopCenter) {
                // Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(28.dp))
                        .border(3.dp, Yellow, RoundedCornerShape(28.dp))
                        .padding(top = 56.dp, start = 24.dp, end = 24.dp, bottom = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "What's your name?",
                        fontFamily = Baloo2,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp,
                        color = TextDark,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "The book is calling for you!",
                        fontFamily = Nunito,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = TextMid,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it.take(20) },
                        placeholder = {
                            Text(
                                "Enter your name...",
                                fontFamily = Nunito,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color(0xFFB0A0C0)
                            )
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Purple,
                            unfocusedBorderColor = Color(0xFFD8B4FE),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = TextDark,
                            unfocusedTextColor = TextDark
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = Nunito,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { if (nameInput.isNotBlank()) onConfirm(nameInput.trim()) }
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = { if (nameInput.isNotBlank()) onConfirm(nameInput.trim()) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(999.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                        enabled = nameInput.isNotBlank()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (nameInput.isNotBlank())
                                        Brush.horizontalGradient(listOf(Color(0xFF7C3AED), Color(0xFFA78BFA)))
                                    else
                                        Brush.horizontalGradient(listOf(Color(0xFFCCC), Color(0xFFBBB))),
                                    RoundedCornerShape(999.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Open the Book ✨",
                                fontFamily = Baloo2,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "⭐🌟💫✨⭐",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // Top floating book emoji
                FloatingTopEmoji()
            }
        }

        // Back button
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            TextButton(
                onClick = onBack,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = "← Back",
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextMid
                )
            }
        }
    }
}

@Composable
private fun FloatingTopEmoji() {
    val infiniteTransition = rememberInfiniteTransition(label = "bookTop")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bookTopY"
    )
    Text(
        text = "📖",
        fontSize = 72.sp,
        lineHeight = 80.sp,
        modifier = Modifier
            .offset(y = (-44 + offsetY).dp)
    )
}

@Composable
private fun BgDecoEmoji() {
    val infiniteTransition = rememberInfiniteTransition(label = "bgDeco")
    val star1Y by infiniteTransition.animateFloat(
        0f, -10f,
        infiniteRepeatable(tween(2200, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "s1"
    )
    val star2Y by infiniteTransition.animateFloat(
        0f, -8f,
        infiniteRepeatable(tween(2800, easing = FastOutSlowInEasing, delayMillis = 500), RepeatMode.Reverse),
        label = "s2"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Text("🌟", fontSize = 28.sp, modifier = Modifier
            .offset(x = 24.dp, y = (60 + star1Y).dp))
        Text("✨", fontSize = 24.sp, modifier = Modifier
            .offset(x = 310.dp, y = (80 + star2Y).dp))
        Text("🍀", fontSize = 26.sp, modifier = Modifier
            .offset(x = 28.dp, y = 580.dp))
        Text("🌈", fontSize = 30.sp, modifier = Modifier
            .offset(x = 300.dp, y = 560.dp))
    }
}
