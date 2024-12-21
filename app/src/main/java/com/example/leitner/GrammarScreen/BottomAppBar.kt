package com.example.leitner.GrammarScreen


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.leitner.ui.theme.iranyekanmobile
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import android.Manifest
import android.content.pm.PackageManager
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.compose.runtime.*


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
            //modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                GrammarTopAppBar(
                    isEditMode = state.isEditMode,
                    onEditModeToggle = onEditModeToggle,
                    //scrollBehavior =
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
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Add item",
            tint = MaterialTheme.colorScheme.onPrimary
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
            //.height(600.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "اضافه کردن گرامر جدید",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val titles = listOf("ایجاد", "وارد کردن")
        TabRow(selectedTabIndex = currentTabIndex) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = currentTabIndex == index,
                    onClick = { onTabChange(index) },
                    text = {
                        Text(
                            title,
                            fontFamily = iranyekanmobile,
                            color = if (currentTabIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }

        if (currentTabIndex == 0) {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = onTextFieldChange,
                label = {
                    Text(
                        "نام فصل جدید را وارد کنید",
                        fontFamily = iranyekanmobile,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                },
                isError = isTextFieldError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(28.dp)
            )

            if (isTextFieldError) {
                Text(
                    text = "متن باید ۲۰ کاراکتر یا کمتر باشد.",
                    fontFamily = iranyekanmobile,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            val context = LocalContext.current

// وضعیت ذخیره URI عکس
            val imageUri = remember { mutableStateOf<Uri?>(null) }

// لانچر برای باز کردن گالری
            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                if (uri != null) {
                    imageUri.value = uri
                    Toast.makeText(context, "عکس انتخاب شد", Toast.LENGTH_SHORT).show()
                }
            }

// لانچر برای درخواست مجوز
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    galleryLauncher.launch("image/*")  // باز کردن گالری اگر مجوز داده شد
                } else {
                    Toast.makeText(context, "مجوز دسترسی به گالری رد شد", Toast.LENGTH_SHORT).show()
                }
            }

// بررسی و درخواست مجوز بر اساس نسخه API
            Button(
                onClick = {
                    when {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_MEDIA_IMAGES
                        ) == PackageManager.PERMISSION_GRANTED ||
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ) == PackageManager.PERMISSION_GRANTED -> {
                            // اگر مجوز قبلاً داده شده، گالری را باز کن
                            galleryLauncher.launch("image/*")
                        }

                        else -> {
                            // درخواست مجوز بر اساس نسخه API
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            } else {
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "انتخاب عکس از گالری",
                    fontFamily = iranyekanmobile,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Button(
                onClick = onSave,
                enabled = textFieldValue.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    MaterialTheme.colorScheme.primaryContainer
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "ذخیره",
                    fontFamily = iranyekanmobile,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }
        if (currentTabIndex == 1) {
            val context = LocalContext.current

            // وضعیت ذخیره URI فایل انتخاب شده
            var selectedFileName by remember { mutableStateOf<String?>(null) }

            // لانچر برای باز کردن فایل منیجر و انتخاب فایل
            val fileLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenDocument()
            ) { uri: Uri? ->
                if (uri != null) {
                    selectedFileName = getFileName(context, uri)  // گرفتن نام فایل انتخاب شده
                    Toast.makeText(context, "فایل انتخاب شد: $selectedFileName", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, "هیچ فایلی انتخاب نشد", Toast.LENGTH_SHORT).show()
                }
            }

            // دکمه برای باز کردن فایل منیجر
            Button(
                onClick = {
                    // فیلتر کردن نوع فایل‌ها (در اینجا تمام فایل‌ها مجاز هستند: "*/*")
                    fileLauncher.launch(arrayOf("*/*"))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(60.dp)
                    ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = Color.Black
                )
            ) {
                Text("انتخاب فایل")
            }

            // نمایش نام فایل انتخاب شده (در صورت وجود)
//            selectedFileName?.let {
//                Text(
//                    text = "فایل انتخاب شده: $it",
//                    modifier = Modifier.padding(8.dp),
//                    color = MaterialTheme.colorScheme.onSurface
//                )
//            }

        }





        Spacer(modifier = Modifier.height(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GrammarTopAppBar(
    isEditMode: Boolean,
    onEditModeToggle: () -> Unit,
    //scrollBehavior: TopAppBarScrollBehavior
) {
    Column {
        TopAppBar(
            title = { Text("جعبه لایتنر") },
            actions = {
                IconButton( onClick = {/*onEditModeToggle*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = if (isEditMode) "پایان ویرایش" else "شروع ویرایش"
                    )
                }
            },
            //scrollBehavior = scrollBehavior,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                //scrolledContainerColor = Color.Transparent, // رنگ پس‌زمینه در حالت اسکرول
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground, // رنگ آیکون‌های navigation
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground // رنگ آیکون‌های action
            )
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            thickness = 1.dp
        )
    }

}


private fun getFileName(context: android.content.Context, uri: Uri): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex >= 0) return it.getString(nameIndex)
        }
    }
    return null
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

