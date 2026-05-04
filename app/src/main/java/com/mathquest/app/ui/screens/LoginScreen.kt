package com.mathquest.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mathquest.app.R
import com.mathquest.app.ui.theme.Baloo2
import com.mathquest.app.ui.theme.Nunito
import com.mathquest.app.ui.theme.TextDark

private const val LOGIN_PANEL_ASPECT = 823f / 945f
private const val START_BTN_ASPECT = 538f / 121f

@Composable
fun LoginScreen(
    onSubmit: (String) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_main_menu),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
        )

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Aspect-locked panel sizing
            val maxPanelHeight = maxHeight * 0.95f
            val maxPanelWidth = maxWidth * 0.55f
            val panelWidth: androidx.compose.ui.unit.Dp
            val panelHeight: androidx.compose.ui.unit.Dp
            if (maxPanelWidth / maxPanelHeight < LOGIN_PANEL_ASPECT) {
                panelWidth = maxPanelWidth
                panelHeight = panelWidth / LOGIN_PANEL_ASPECT
            } else {
                panelHeight = maxPanelHeight
                panelWidth = panelHeight * LOGIN_PANEL_ASPECT
            }

            Box(
                modifier = Modifier
                    .width(panelWidth)
                    .height(panelHeight)
            ) {
                // Asset background
                Image(
                    painter = painterResource(id = R.drawable.bg_login_window),
                    contentDescription = "Login Window",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                // === Sequential layout — guaranteed in-order vertical positioning ===
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top spacer — clears HELLO! and decorations, lands on the Enter Name box
                    Spacer(modifier = Modifier.height(panelHeight * 0.385f))

                    // Text field — sits exactly inside the Enter Name pencil rectangle
                    // Half the previous length: now ~24% of panel width, centered
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = panelWidth * 0.38f,
                                end = panelWidth * 0.38f
                            )
                            .height(panelHeight * 0.068f)
                    ) {
                        BasicTextField(
                            value = name,
                            onValueChange = { name = it.take(12) },
                            modifier = Modifier.fillMaxSize(),
                            textStyle = TextStyle(
                                fontFamily = Baloo2,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = TextDark,
                                textAlign = TextAlign.Center
                            ),
                            cursorBrush = SolidColor(TextDark),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = {
                                if (name.isNotBlank()) onSubmit(name.trim())
                            }),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // White cover hides the asset's "Enter Name..." pencil text
                                    // so the user's typed name reads cleanly inside the rectangle
                                    if (name.isNotEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.White)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                    }

                    // Small gap so Start sits just under the Enter Name box,
                    // above the character art at ~55% of panel
                    Spacer(modifier = Modifier.height(panelHeight * 0.025f))

                    // Start button — between Enter Name and the character drawings
                    Image(
                        painter = painterResource(id = R.drawable.btn_start),
                        contentDescription = "Start Adventure",
                        modifier = Modifier
                            .width(panelWidth * 0.40f)
                            .aspectRatio(START_BTN_ASPECT)
                            .alpha(if (name.isNotBlank()) 1f else 0.5f)
                            .clickable(
                                enabled = name.isNotBlank(),
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (name.isNotBlank()) onSubmit(name.trim())
                            },
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Text(
                text = "← Back",
                fontFamily = Nunito,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 18.dp, bottom = 14.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onBack() }
                    .padding(8.dp)
            )
        }
    }
}
