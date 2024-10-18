//package com.example.leitner.GrammarScreen
//
//
//import android.content.Context
//import android.view.accessibility.AccessibilityManager
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.ArrowForward
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.BottomAppBar
//import androidx.compose.material3.BottomAppBarDefaults
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
//import androidx.compose.material3.FabPosition
//import androidx.compose.material3.FilledIconButton
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.FloatingActionButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LargeTopAppBar
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.MediumTopAppBar
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.TopAppBarTitleAlignment
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//
//
///**
// * A sample for a vibrant [BottomAppBar] that collapses when the content is scrolled up, and appears
// * when the content scrolled down. The content arrangement is fixed.
// */
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
//@Preview
//@Composable
//fun ExitAlwaysBottomAppBarFixedVibrant() {
//    val context = LocalContext.current
//    val isTouchExplorationEnabled = remember {
//        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
//        am.isEnabled && am.isTouchExplorationEnabled
//    }
//    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
//    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        bottomBar = {
//            BottomAppBar(
//                horizontalArrangement = BottomAppBarDefaults.HorizontalArrangement,
//                scrollBehavior = if (!isTouchExplorationEnabled) scrollBehavior else null,
//                containerColor =
//                MaterialTheme.colorScheme.primaryContainer, // TODO(b/356885344): tokens
//                content = {
//                    IconButton(onClick = { /* doSomething() */ }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Localized description"
//                        )
//                    }
//                    IconButton(onClick = { /* doSomething() */ }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowForward,
//                            contentDescription = "Localized description"
//                        )
//                    }
//                    FilledIconButton(
//                        modifier = Modifier.width(56.dp),
//                        onClick = { /* doSomething() */ }
//                    ) {
//                        Icon(Icons.Filled.Add, contentDescription = "Localized description")
//                    }
//                    IconButton(onClick = { /* doSomething() */ }) {
//                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
//                    }
//                    IconButton(onClick = { /* doSomething() */ }) {
//                        Icon(Icons.Filled.Edit, contentDescription = "Localized description")
//                    }
//                }
//            )
//        },
//        content = { innerPadding ->
//            LazyColumn(
//                contentPadding = innerPadding,
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                val list = (0..75).map { it.toString() }
//                items(count = list.size) {
//                    Text(
//                        text = list[it],
//                        style = MaterialTheme.typography.bodyLarge,
//                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
//                    )
//                }
//            }
//        }
//    )
//}
