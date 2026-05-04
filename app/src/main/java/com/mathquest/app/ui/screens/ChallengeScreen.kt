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
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.GameState
import com.mathquest.app.data.CHAPTERS
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Cream
import com.mathquest.app.ui.theme.GreenDark
import com.mathquest.app.ui.theme.Lavender
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.Purple
import com.mathquest.app.ui.theme.TextDark
import com.mathquest.app.ui.theme.TextMid
import com.mathquest.app.ui.theme.WrongRed

@Composable
fun ChallengeScreen(
    state: GameState,
    onSubmit: (String) -> Unit,
    onUseHint: () -> Unit,
    onBack: () -> Unit
) {
    val chapter = CHAPTERS[state.currentChapter]
    val problem = chapter.problems[state.currentProblem]
    val total = chapter.problems.size

    var inputValue by remember(state.currentProblem) { mutableStateOf("") }
    val isCorrectState = state.isCorrectFeedback && state.feedbackMessage != null
    val isWrongState = !state.isCorrectFeedback && state.feedbackMessage != null

    val progressFraction = state.currentProblem.toFloat() / total.toFloat()
    val progressAnim by animateFloatAsState(
        targetValue = progressFraction,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "progress"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "monster")
    val monsterY by infiniteTransition.animateFloat(
        0f, -8f,
        infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
        label = "mY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp, start = 18.dp, end = 18.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Lavender, CircleShape)
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text("←", fontSize = 18.sp, fontFamily = Baloo2, fontWeight = FontWeight.Bold, color = Purple)
            }

            // Progress bar
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFE9E4F0))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressAnim)
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(listOf(Purple, Color(0xFFA78BFA))),
                            RoundedCornerShape(10.dp)
                        )
                )
            }

            Text(
                text = "${state.currentProblem + 1}/$total",
                fontFamily = Nunito,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                color = TextMid
            )
        }

        // Hearts row — flex row, no wrapping
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5) { i ->
                Text(
                    text = if (i < state.hearts) "❤️" else "🖤",
                    fontSize = 22.sp,
                    lineHeight = 24.sp,
                    modifier = Modifier.let {
                        if (i >= state.hearts) it.then(Modifier) else it
                    }
                )
            }
        }

        // Streak banner
        AnimatedVisibility(
            visible = state.showStreakBanner,
            enter = scaleIn(tween(300)) + fadeIn(tween(300)),
            exit = fadeOut(tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 6.dp)
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFFFDE68A), Color(0xFFFCD34D))),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🔥", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${state.streakCount} in a row! +1 heart restored!",
                        fontFamily = Nunito,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color(0xFFB45309)
                    )
                }
            }
        }

        // Problem card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp)
                .shadow(8.dp, RoundedCornerShape(24.dp))
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(18.dp)
        ) {
            // Monster emoji — dedicated block
            Text(
                text = chapter.emoji,
                fontSize = 44.sp,
                lineHeight = 52.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = monsterY.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Problem number label
            Text(
                text = "PROBLEM ${state.currentProblem + 1} OF $total",
                fontFamily = Nunito,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 12.sp,
                color = TextMid,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Question text — no emoji mixed in
            Text(
                text = problem.question,
                fontFamily = Nunito,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextDark,
                lineHeight = 23.sp
            )
        }

        // Hint box
        AnimatedVisibility(
            visible = state.showingHint && state.hintText != null,
            enter = scaleIn(tween(300)) + fadeIn(tween(300)),
            exit = fadeOut(tween(200))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 6.dp)
                    .background(
                        Brush.horizontalGradient(listOf(Color(0xFFFFF3C4), Color(0xFFFDE68A))),
                        RoundedCornerShape(16.dp)
                    )
                    .border(2.dp, Color(0xFFFCD34D), RoundedCornerShape(16.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Hint emoji in own span
                Text(text = "💡", fontSize = 18.sp, lineHeight = 22.sp)
                // Hint text — separate
                Text(
                    text = state.hintText ?: "",
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF92400E),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Feedback banner
        AnimatedVisibility(
            visible = state.feedbackMessage != null,
            enter = scaleIn(tween(300)) + fadeIn(tween(300)),
            exit = fadeOut(tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 6.dp)
                    .background(
                        if (isCorrectState) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                        RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.feedbackMessage ?: "",
                    fontFamily = Baloo2,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    color = if (isCorrectState) GreenDark else WrongRed,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Answer section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Answer input
            OutlinedTextField(
                value = inputValue,
                onValueChange = { inputValue = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text(
                        "Type your answer...",
                        fontFamily = Nunito,
                        fontSize = 16.sp,
                        color = Color(0xFFB0A0C0)
                    )
                },
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = when {
                        isCorrectState -> GreenDark
                        isWrongState -> WrongRed
                        else -> Purple
                    },
                    unfocusedBorderColor = when {
                        isCorrectState -> GreenDark
                        isWrongState -> WrongRed
                        else -> Lavender
                    },
                    focusedContainerColor = when {
                        isCorrectState -> Color(0xFFDCFCE7)
                        isWrongState -> Color(0xFFFEE2E2)
                        else -> Color.White
                    },
                    unfocusedContainerColor = when {
                        isCorrectState -> Color(0xFFDCFCE7)
                        isWrongState -> Color(0xFFFEE2E2)
                        else -> Color.White
                    },
                    focusedTextColor = TextDark,
                    unfocusedTextColor = TextDark
                ),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontFamily = Nunito,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSubmit(inputValue) }
                )
            )

            // Submit button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.horizontalGradient(listOf(Purple, Color(0xFF5B21B6)))
                    )
                    .shadow(5.dp, RoundedCornerShape(18.dp))
                    .clickable { onSubmit(inputValue) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓  Check Answer",
                    fontFamily = Baloo2,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            // Hint button (shown only when hearts <= 2 and hints left)
            if (state.hearts <= 2 && state.hintsLeft > 0 && !state.showingHint) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White)
                        .border(2.dp, Color(0xFFFCD34D), RoundedCornerShape(18.dp))
                        .clickable { onUseHint() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "💡", fontSize = 16.sp)
                        Text(
                            text = "Hint (${state.hintsLeft} left)",
                            fontFamily = Nunito,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = Color(0xFFB45309)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
