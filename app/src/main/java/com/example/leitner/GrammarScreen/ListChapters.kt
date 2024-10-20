package com.example.leitner.GrammarScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListChapters() {
    var isEditMode by remember { mutableStateOf(false) }
    var chapters by remember { mutableStateOf((1..10).map { "فصل $it" }) }
    var draggedItemIndex by remember { mutableStateOf<Int?>(null) }
    var targetIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("جعبه لایتنر") },
                actions = {
                    IconButton(onClick = { isEditMode = !isEditMode }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = if (isEditMode) "پایان ویرایش" else "شروع ویرایش"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 8.dp,
                start = 8.dp,
                end = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = chapters,
                key = { index, item -> item }
            ) { index, chapter ->
                ChapterItem(
                    title = chapter,
                    isEditMode = isEditMode,
                    isDragging = index == draggedItemIndex,
                    onDragStart = {
                        draggedItemIndex = index
                    },
                    onDrag = { offset ->
                        if (isEditMode) {
                            // محاسبه موقعیت جدید با توجه به ارتفاع آیتم
                            val itemHeight = 80.dp.value
                            val newPosition = (offset.y / itemHeight).roundToInt()
                            targetIndex = (index + newPosition).coerceIn(0, chapters.size - 1)
                        }
                    },
                    onDragEnd = {
                        if (isEditMode && draggedItemIndex != null && targetIndex != null) {
                            val fromIndex = draggedItemIndex!!
                            val toIndex = targetIndex!!
                            if (fromIndex != toIndex) {
                                chapters = chapters.toMutableList().apply {
                                    add(toIndex, removeAt(fromIndex))
                                }
                            }
                        }
                        draggedItemIndex = null
                        targetIndex = null
                    }
                )
            }
        }
    }
}

@Composable
fun ChapterItem(
    title: String,
    isEditMode: Boolean,
    isDragging: Boolean,
    onDragStart: () -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit
) {
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .then(
                if (isEditMode) {
                    Modifier.pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { onDragStart() },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffset += dragAmount
                                onDrag(dragOffset)
                            },
                            onDragEnd = {
                                dragOffset = Offset.Zero
                                onDragEnd()
                            },
                            onDragCancel = {
                                dragOffset = Offset.Zero
                                onDragEnd()
                            }
                        )
                    }
                } else Modifier
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDragging) 8.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isEditMode) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Gray, CircleShape)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}