package cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import cz.mendelu.xhalachk.smartwastebrno.analyzers.CustomModelObjectDetector
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.home.HomeScreenUIState

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class PhotoPickerScreenViewModel @Inject constructor(
) : ViewModel(), PhotoPickerScreenActions {

    private val _uiState: MutableStateFlow<PhotoPickerScreenUIState> =
        MutableStateFlow(value = PhotoPickerScreenUIState())
    val uiState: StateFlow<PhotoPickerScreenUIState> get() = _uiState


    override fun onImageSelected(uri: Uri?) {
        uri?.let {
            _uiState.value = _uiState.value.copy(selectedImageUri = it)

        }
    }
}