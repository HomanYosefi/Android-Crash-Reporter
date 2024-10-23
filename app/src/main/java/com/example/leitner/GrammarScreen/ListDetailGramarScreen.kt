package com.example.leitner.GrammarScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

data class WordItem(
    val id: Int,
    val title: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailGrammarScreen(
    navController: NavHostController,
    chapterTitle: String
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDetail by remember { mutableStateOf(false) }

    val words = remember {
        listOf(
            WordItem(1, "کلمه اول"),
            WordItem(2, "کلمه دوم"),
            WordItem(3, "کلمه سوم"),
            WordItem(1, "کلمه اول"),
            WordItem(2, "کلمه دوم"),
            WordItem(3, "کلمه سوم"),
            WordItem(1, "کلمه اول"),
            WordItem(2, "کلمه دوم"),
            WordItem(3, "کلمه سوم"),
            WordItem(1, "کلمه اول"),
            WordItem(2, "کلمه دوم"),
            WordItem(3, "کلمه سوم"),
            WordItem(1, "کلمه اول"),
            WordItem(2, "کلمه دوم"),
            WordItem(3, "کلمه سوم"),
            WordItem(1, "کلمه اول"),
            WordItem(2, "کلمه دوم"),
            WordItem(3, "کلمه سوم")
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { Text(chapterTitle) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "برگشت"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    CustomCircularProgress(targetProgress = 0.75f, displayNumber = 42)
                    Spacer(modifier = Modifier.weight(1f))
                }

                Button(
                    onClick = { showDetail = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Study cards")
                }

                LazyColumn {
                    items(words) { word ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = word.title,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showDetail,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("جزئیات") },
                        navigationIcon = {
                            IconButton(onClick = { showDetail = false }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "برگشت"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            titleContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "سلام",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
    }
}


@Composable
fun CustomCircularProgress(
    targetProgress: Float, // مقدار هدف
    displayNumber: Int, // عددی که نمایش داده می‌شود
    modifier: Modifier = Modifier,
    animationDuration: Int = 1000, // مدت زمان انیمیشن به میلی‌ثانیه
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    // نگه‌داری پیشرفت انیمیشن شده
    var progress by remember { mutableStateOf(0f) }

    // اجرای انیمیشن از 0 تا مقدار هدف
    LaunchedEffect(targetProgress) {
        // انیمیشن تدریجی مقدار پیشرفت
        animate(
            initialValue = 0f,
            targetValue = targetProgress,
            animationSpec = tween(durationMillis = animationDuration)
        ) { value, _ ->
            progress = value
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(100.dp)
    ) {
        CircularProgressIndicator(
            progress = progress, // مقدار انیمیشن شده
            modifier = Modifier.fillMaxSize(),
            trackColor = trackColor,
            color = color,
            strokeWidth = 8.dp
        )

        // نمایش عدد در مرکز دایره
        Text(
            text = displayNumber.toString(),
            style = TextStyle(
                fontSize = 24.sp,
                color = textColor
            )
        )
    }
}
