package cz.mendelu.xhalachk.smartwastebrno.ui.screens.detection

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import cz.mendelu.xhalachk.smartwastebrno.R
import cz.mendelu.xhalachk.smartwastebrno.analyzers.CustomModelObjectDetector
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.home.HomeScreenUIState
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker.PhotoPickerScreenActions
import cz.mendelu.xhalachk.smartwastebrno.ui.screens.photopicker.PhotoPickerScreenUIState

import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DetectionScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
   private val customDetector: CustomModelObjectDetector
) : ViewModel(), DetectionScreenActions {


    private val _uiState: MutableStateFlow<DetectionScreenUIState> =
        MutableStateFlow(value = DetectionScreenUIState())
    val uiState: StateFlow<DetectionScreenUIState> get() = _uiState

    private var labelList: List<String> = emptyList()


    /*
    private val customDetector = CustomModelObjectDetector(
        detectionMode = CustomObjectDetectorOptions.SINGLE_IMAGE_MODE
    ) { objects, width, height ->
        handleDetectionResults(objects)
    }

     */


    init {
        loadAndCleanLabels()
    }


    private fun loadAndCleanLabels() {
        try {
            val garbageRegex = Regex("^\\\\s*")

            labelList = context.assets.open("labels.txt")
                .bufferedReader()
                .use { it.readLines() }
                .map { line ->
                    line.replace(garbageRegex, "").trim()
                }
        } catch (e: IOException) {
            Log.e("WasteApp", "Error reading labels.txt", e)
        }
    }

    override fun startAnalyze() {
        val uri = _uiState.value.imageUri!!.toUri()
            _uiState.value = _uiState.value.copy(resultText = "Analyzing...", analysisPerformed = false)
            analyzeImage(uri)
    }

    override fun setIncomingImageUri(uriString: String?) {
        _uiState.value = _uiState.value.copy(imageUri = uriString, resultText = "", analysisPerformed = false)
    }

    override fun analyzeImage(uri: Uri) {

        try {
            val image = InputImage.fromFilePath(context, uri)
            customDetector.processInputImage(image) { objects, width, height ->
                handleDetectionResults(objects)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            _uiState.value = _uiState.value.copy(resultText = "Error: Could not load image file.", analysisPerformed = true)
        }
    }

    override fun handleDetectionResults(objects: List<DetectedObject>) {
        if (objects.isEmpty()) {
           _uiState.value = _uiState.value.copy(resultText = "No objects detected.", analysisPerformed = true)
            return
        }
        val firstObject = objects.first()
        val firstLabelObj = firstObject.labels.firstOrNull()
        val labelIndex = firstLabelObj?.index ?: -1
        val rawLabelName = if (labelIndex in labelList.indices) {
            labelList[labelIndex]
        } else {
            "Unknown"
        }

        val wasteType = classifyWasteByText(rawLabelName)
        _uiState.value = _uiState.value.copy(resultText = wasteType, analysisPerformed = true)
    }
    fun classifyWasteByText(label: String): String {
        val lowerLabel = label.lowercase()
        return when {
            lowerLabel.contains("glass") ||
                    lowerLabel.contains("beer") ||
                    lowerLabel.contains("wine") ||
                    lowerLabel.contains("pitcher") -> "Glass"

            lowerLabel.contains("plastic") ||
                    lowerLabel.contains("bottle") ||
                    lowerLabel.contains("poly") -> "Plastic"

            lowerLabel.contains("metal") ||
                    lowerLabel.contains("aluminum") ||
                    lowerLabel.contains("can") ||
                    lowerLabel.contains("tin") -> "Metal"

            lowerLabel.contains("paper") ||
                    lowerLabel.contains("carton") ||
                    lowerLabel.contains("book") ||
                    lowerLabel.contains("cardboard") -> "Paper"

            lowerLabel.contains("apple") ||
                    lowerLabel.contains("banana") ||
                    lowerLabel.contains("food") -> "Bio Waste"
            else -> "Mixed Waste"
        }
    }
}