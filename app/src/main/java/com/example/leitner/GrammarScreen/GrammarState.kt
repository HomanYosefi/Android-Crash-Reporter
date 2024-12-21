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
//data class Chapter(
//    val title: String,
//    val imageUrl: String,
//    val wordCount: Int,
//    val progressPercentage: Float
//)
//
//data class GrammarState(
//    val isEditMode: Boolean = false,
//    val showBottomSheet: Boolean = false,
//    val showScrollToTop: Boolean = false,
//    val chapters: List<Chapter> = (1..40).map { chapterNumber ->
//        Chapter(
//            title = "فصل $chapterNumber",
//            imageUrl = "", // مسیر عکس مربوط به هر فصل
//            wordCount = 0, // تعداد کلمات هر فصل
//            progressPercentage = 0f // درصد پیشرفت هر فصل
//        )
//    },
//    val draggedItemIndex: Int? = null,
//    val targetIndex: Int? = null,
//    val currentTabIndex: Int = 0,
//    val textFieldValue: String = "",
//    val isTextFieldError: Boolean = false
//)