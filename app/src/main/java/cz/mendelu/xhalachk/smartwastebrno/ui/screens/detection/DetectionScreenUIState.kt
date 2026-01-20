package cz.mendelu.xhalachk.smartwastebrno.ui.screens.detection

import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel

data class DetectionScreenUIState (
    var imageUri: String? = null,
    val resultText: String = "",
    val analysisPerformed: Boolean = false
)