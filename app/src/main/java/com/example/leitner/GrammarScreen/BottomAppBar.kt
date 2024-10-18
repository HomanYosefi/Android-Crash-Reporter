package com.example.leitner.GrammarScreen

import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrammarScreen(
    onAddClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    paddingValues: Dp,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState
) {
    val context = LocalContext.current
    val isTouchExplorationEnabled = remember {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        am.isEnabled && am.isTouchExplorationEnabled
    }

    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val showScrollToTop by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Box(
        modifier = modifier
            .padding(bottom = paddingValues)
    ) {

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            bottomBar = {
                BottomAppBar(
                    actions = {
                        if (showScrollToTop) {
                            FilledIconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        listState.animateScrollToItem(0)
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Scroll to top"
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                //AddGrammarPage(scope.launch { scaffoldState.bottomSheetState.partialExpand() }, scaffoldState)
                                BottomSheetScaffold(
                                    scaffoldState = scaffoldState,
                                    sheetPeekHeight = 128.dp,
                                    sheetContent = {
                                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                            Box(Modifier.fillMaxWidth().height(128.dp), contentAlignment = Alignment.Center) {
                                                Text("Swipe up to expand sheet")
                                            }
                                            Text("Sheet content")
                                            Button(
                                                modifier = Modifier.padding(bottom = 64.dp),
                                                onClick = { Job }
                                            ) {
                                                Text("Click to collapse sheet")
                                            }
                                        }
                                    }
                                ) { innerPadding ->
                                    Box(
                                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Scaffold Content")
                                    }
                                }

                            },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            // modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add item",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    scrollBehavior = if (!isTouchExplorationEnabled) scrollBehavior else null,
                    containerColor = Color.Transparent,
                    //modifier = Modifier.height(110.dp)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}
