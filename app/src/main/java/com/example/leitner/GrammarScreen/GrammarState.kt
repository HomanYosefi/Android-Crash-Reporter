package com.example.leitner.GrammarScreen

data class GrammarState(
    val isEditMode: Boolean = false,
    val showBottomSheet: Boolean = false,
    val showScrollToTop: Boolean = false,
    val chapters: List<String> = (1..40).map { "فصل $it" },
    val draggedItemIndex: Int? = null,
    val targetIndex: Int? = null,
    val currentTabIndex: Int = 0,
    val textFieldValue: String = "",
    val isTextFieldError: Boolean = false
)