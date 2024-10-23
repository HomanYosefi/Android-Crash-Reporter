package com.example.leitner.GrammarScreen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FlashCard(
    frontText: String,
    backText: String,
    modifier: Modifier = Modifier
) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ),
        label = "card_rotation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp)
            .background(Color.Transparent)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .clickable { isFlipped = !isFlipped }
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                // Front side
                if (rotation <= 90f) {
                    Text(
                        text = frontText,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = 1f - (rotation / 90f)
                            }
                    )
                }
                // Back side
                if (rotation > 90f) {
                    Text(
                        text = backText,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .graphicsLayer {
                                rotationY = 180f
                                alpha = (rotation - 90f) / 90f
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun FlashCardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FlashCard(
            frontText = "Hello",
            backText = "سلام",
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "برای مشاهده ترجمه کارت را لمس کنید",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}