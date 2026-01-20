package cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker

import android.net.Uri

interface PhotoPickerScreenActions {
    fun onImageSelected(uri: Uri?)
}