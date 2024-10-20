package com.example.leitner.GrammarScreen

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.runtime.*




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Grammar(
    navController: NavHostController,
    viewModel: GrammarViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    GrammarScreen(
        state = uiState,
        onEditModeToggle = viewModel::toggleEditMode,
        onDragStart = viewModel::updateDraggedItem,
        onDragEnd = { fromIndex, toIndex ->
            viewModel.moveItem(fromIndex, toIndex)
        },
        onBottomSheetToggle = viewModel::toggleBottomSheet,
        onTabChange = viewModel::updateTabIndex,
        onTextFieldChange = viewModel::updateTextField,
        modifier = Modifier,
        paddingValues = 70.dp
    )
}

