package com.example.leitner.GrammarScreen
//
//import android.app.Activity
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.provider.MediaStore
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.room.Dao
//import androidx.room.Database
//import androidx.room.Entity
//import androidx.room.Insert
//import androidx.room.PrimaryKey
//import androidx.room.RoomDatabase
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.room.Query
//import coil.compose.rememberAsyncImagePainter
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import coil.compose.AsyncImage
//
//
//// Database
//@Dao
//interface ImageDao {
//    @Insert
//    suspend fun insert(image: Image)
//
//    @Query("SELECT * FROM images")
//    fun getAllImages(): List<Image>
//}
//
//@Entity(tableName = "images")
//data class Image(
//    @PrimaryKey(autoGenerate = true) val id: Long = 0,
//    val imageUri: String
//)
//
//@Database(entities = [Image::class], version = 1)
//abstract class ImageDatabase : RoomDatabase() {
//    abstract fun imageDao(): ImageDao
//}
//
//// UI
//@Composable
//fun ImageGalleryScreen(
//    viewModel: ImageGalleryViewModel = hiltViewModel()
//) {
//    val context = LocalContext.current
//    val images: List<Image> = viewModel.images.collectAsState(initial = emptyList()).value
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        LazyColumn(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(16.dp)
//        ) {
//            items(images) { image ->
//                ImageItem(image)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        FloatingActionButton(
//            onClick = { viewModel.openImagePicker(context) }
//        ) {
//            Icon(Icons.Default.Add, contentDescription = "Add Image")
//        }
//    }
//}
//
//@Composable
//fun ImageItem(image: Image) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        elevation = 4.dp
//    ) {
//        AsyncImage(
//            model = image.imageUri,
//            contentDescription = null, // You can add a more descriptive text here if needed
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}
//
//// ViewModel
//class ImageGalleryViewModel @Inject constructor(
//    private val imageDatabase: ImageDatabase
//) : ViewModel() {
//
//    private val _images = MutableStateFlow<List<Image>>(emptyList())
//    val images: StateFlow<List<Image>> = _images.asStateFlow()
//
//    init {
//        loadImages()
//    }
//
//    @Composable
//    fun openImagePicker(context: Context) {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        val launcher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val imageUri: Uri? = result.data?.data
//
//                imageUri?.let { uri ->
//                    viewModelScope.launch {
//                        imageDatabase.imageDao().insert(Image(imageUri = uri.toString()))
//                        loadImages()
//                    }
//                }
//            }
//        }
//        launcher.launch(intent)
//    }
//
//    private fun loadImages() {
//        viewModelScope.launch {
//            _images.value = imageDatabase.imageDao().getAllImages()
//        }
//    }
//}

