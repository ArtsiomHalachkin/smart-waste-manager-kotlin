package cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel

data class PhotoPickerScreenUIState (
    val selectedImageUri: Uri? = null,
)