package com.example.leitner.GrammarScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GrammarViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(GrammarState())
    val uiState: StateFlow<GrammarState> = _uiState.asStateFlow()

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditMode = !it.isEditMode) }
    }

    fun updateDraggedItem(index: Int?) {
        _uiState.update { it.copy(draggedItemIndex = index) }
    }

    fun updateTargetIndex(index: Int?) {
        _uiState.update { it.copy(targetIndex = index) }
    }

    fun moveItem(fromIndex: Int, toIndex: Int) {
        _uiState.update { currentState ->
            val newChapters = currentState.chapters.toMutableList().apply {
                add(toIndex, removeAt(fromIndex))
            }
            currentState.copy(
                chapters = newChapters,
                draggedItemIndex = null,
                targetIndex = null
            )
        }
    }

    fun toggleBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = !it.showBottomSheet) }
    }

    fun updateTabIndex(index: Int) {
        _uiState.update { it.copy(currentTabIndex = index) }
    }

    fun updateTextField(text: String) {
        _uiState.update {
            it.copy(
                textFieldValue = text,
                isTextFieldError = text.length > 20
            )
        }
    }
}