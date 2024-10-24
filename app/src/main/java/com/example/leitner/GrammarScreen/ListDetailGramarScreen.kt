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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.leitner.R
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.Float

data class WordItem(
    val id: Int,
    val englishWord: String,
    val farsiWord: String

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
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم"),
            WordItem(1, "lorem episom", "لورم اپیسوم")


        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = { /*Text(chapterTitle)*/ },
                    navigationIcon = {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "برگشت"
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search"
                                )
                            }
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "share"
                                )
                            }
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
                Text(
                    text = chapterTitle,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp, bottom = 0.dp)
                )

                Text(
                    text = "Cards for today",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    CustomCircularProgress(targetProgress = 0.75f, displayNumber = 42)
                    Spacer(modifier = Modifier.weight(1f))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp) // حداقل ارتفاع باکس
                        .padding(vertical = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StatusColumn(
                            count = "0",
                            label = "To review",
                            iconTint = Color.Green
                        )

                        StatusColumn(
                            count = "10",
                            label = "Not Studied",
                            iconTint = Color.Unspecified
                        )
                    }
                }

                Button(
                    onClick = { showDetail = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "Study cards",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
                Text(
                    text = "Cards in deck",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(16.dp, bottom = 0.dp)
                )
                CustomThumbSlider(5, 10)

                LazyColumn {
                    items(words) { word ->
                        FlashCardScreen(word.englishWord, word.farsiWord)
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
//                    Text(
//                        text = "سلام",
//                        style = MaterialTheme.typography.headlineLarge
//                    )
                    FlashCardScreen("hello", "سلام")
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
        modifier = modifier.size(130.dp)
    ) {
        CircularProgressIndicator(
            progress = progress, // مقدار انیمیشن شده
            modifier = Modifier.fillMaxSize(),
            trackColor = trackColor,
            color = color,
            strokeWidth = 12.dp
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


@Composable
private fun StatusColumn(
    count: String,
    label: String,
    iconTint: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = count,
                fontSize = 26.sp,
                fontWeight = FontWeight.Normal
            )
            Icon(
                painter = painterResource(R.drawable.cap),
                contentDescription = "cap",
                modifier = Modifier.size(40.dp),
                tint = iconTint
            )
        }
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomThumbSlider(
    reviewWords: Int,
    wholeWords: Int
) {
    // محاسبه درصد نهایی
    val targetProgress = remember(reviewWords, wholeWords) {
        if (wholeWords != 0) reviewWords.toFloat() / wholeWords.toFloat() else 0f
    }

    // مقدار اولیه برای انیمیشن از صفر
    var currentProgress by remember { mutableFloatStateOf(0f) }

    // انیمیشن از صفر تا مقدار هدف با تنظیمات سفارشی
    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress,
        animationSpec = tween(
            durationMillis = 1000, // مدت زمان انیمیشن
            easing = FastOutSlowInEasing // نوع حرکت انیمیشن
        )
    )

    // شروع انیمیشن وقتی کامپوننت ایجاد میشه
    LaunchedEffect(targetProgress) {
        currentProgress = targetProgress
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp)),
            trackColor = Color.Gray.copy(alpha = 0.2f), // رنگ پس‌زمینه پروگرس بار
            color = MaterialTheme.colorScheme.primary // رنگ پروگرس
        )
        Spacer(Modifier.requiredHeight(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween // فاصله‌گذاری بهتر
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$wholeWords Not studied",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$reviewWords To review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}