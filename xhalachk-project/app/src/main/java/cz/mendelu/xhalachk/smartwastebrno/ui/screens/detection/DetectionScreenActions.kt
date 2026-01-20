package cz.mendelu.xhalachk.smartwastebrno.ui.screens.detection

import android.net.Uri
import com.google.mlkit.vision.objects.DetectedObject

interface DetectionScreenActions {
    fun startAnalyze()
    fun analyzeImage(uri: Uri)

    fun setIncomingImageUri(uriString: String?)
    fun handleDetectionResults(objects: List<DetectedObject>)
}