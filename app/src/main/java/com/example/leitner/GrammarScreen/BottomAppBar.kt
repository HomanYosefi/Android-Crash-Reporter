package com.example.leitner.GrammarScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrammarScreen(
    state: GrammarState,
    onEditModeToggle: () -> Unit,
    onDragStart: (Int?) -> Unit,
    onDragEnd: (Int, Int) -> Unit,
    onBottomSheetToggle: () -> Unit,
    onTabChange: (Int) -> Unit,
    onTextFieldChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: Dp,
    navController: NavHostController
) {
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()

    // اضافه کردن وضعیت اسکرول
    val showScrollToTop = listState.firstVisibleItemIndex > 0

    Box(modifier = modifier.padding(bottom = paddingValues)) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                GrammarTopAppBar(
                    isEditMode = state.isEditMode,
                    onEditModeToggle = onEditModeToggle,
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            ChaptersList(
                chapters = state.chapters,
                isEditMode = state.isEditMode,
                draggedItemIndex = state.draggedItemIndex,
                onDragStart = onDragStart,
                onDragEnd = onDragEnd,
                listState = listState,  // پاس دادن listState به ChaptersList
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }

        GrammarBottomBar(
            showScrollToTop = showScrollToTop,  // استفاده از مقدار محاسبه شده
            onScrollToTop = {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            onAddClick = onBottomSheetToggle,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        if (state.showBottomSheet) {
            AddGrammarBottomSheet(
                currentTabIndex = state.currentTabIndex,
                textFieldValue = state.textFieldValue,
                isTextFieldError = state.isTextFieldError,
                onDismiss = onBottomSheetToggle,
                onTabChange = onTabChange,
                onTextFieldChange = onTextFieldChange
            )
        }
    }
}


// Composable components

@Composable
private fun ScrollToTopButton(onClick: () -> Unit) {
    FilledIconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "Scroll to top"
        )
    }
}

@Composable
private fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add item",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun AddGrammarContent(
    currentTabIndex: Int,
    textFieldValue: String,
    isTextFieldError: Boolean,
    onTabChange: (Int) -> Unit,
    onTextFieldChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Grammar",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val titles = listOf("Create", "Import")
        TabRow(selectedTabIndex = currentTabIndex) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = currentTabIndex == index,
                    onClick = { onTabChange(index) },
                    text = { Text(title) }
                )
            }
        }

        if (currentTabIndex == 0) {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = onTextFieldChange,
                label = { Text("Enter text") },
                isError = isTextFieldError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            if (isTextFieldError) {
                Text(
                    text = "Text must be 20 characters or less",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GrammarTopAppBar(
    isEditMode: Boolean,
    onEditModeToggle: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { Text("جعبه لایتنر") },
        actions = {
            IconButton(onClick = onEditModeToggle) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = if (isEditMode) "پایان ویرایش" else "شروع ویرایش"
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

@Composable
private fun GrammarBottomBar(
    showScrollToTop: Boolean,
    onScrollToTop: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier,
        actions = {
            if (showScrollToTop) {
                ScrollToTopButton(onClick = onScrollToTop)
            }
        },
        floatingActionButton = {
            AddButton(onClick = onAddClick)
        },
        containerColor = Color.Transparent
    )
}

@Composable
private fun ChaptersList(
    chapters: List<String>,
    isEditMode: Boolean,
    draggedItemIndex: Int?,
    onDragStart: (Int?) -> Unit,
    onDragEnd: (Int, Int) -> Unit,
    listState: LazyListState,  // اضافه کردن پارامتر listState
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val targetIndex = remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        state = listState,  // استفاده از listState
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = chapters,
            key = { _, item -> item }
        ) { index, chapter ->
            ChapterItem(
                title = chapter,
                isEditMode = isEditMode,
                isDragging = index == draggedItemIndex,
                onDragStart = {
                    onDragStart(index)
                },
                onDrag = { offset ->
                    if (isEditMode) {
                        val itemHeight = 80.dp.value
                        val newPosition = (offset.y / itemHeight).roundToInt()
                        targetIndex.value = (index + newPosition).coerceIn(0, chapters.size - 1)
                    }
                },
                onDragEnd = {
                    if (targetIndex.value != null) {
                        onDragEnd(index, targetIndex.value!!)
                    }
                    targetIndex.value = null
                },
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddGrammarBottomSheet(
    currentTabIndex: Int,
    textFieldValue: String,
    isTextFieldError: Boolean,
    onDismiss: () -> Unit,
    onTabChange: (Int) -> Unit,
    onTextFieldChange: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState()
    ) {
        AddGrammarContent(
            currentTabIndex = currentTabIndex,
            textFieldValue = textFieldValue,
            isTextFieldError = isTextFieldError,
            onTabChange = onTabChange,
            onTextFieldChange = onTextFieldChange,
            onSave = onDismiss
        )
    }
}

