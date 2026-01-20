package cz.mendelu.xhalachk.smartwastebrno.analyzers

import android.util.Log
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.DetectedObject
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions

class CustomModelObjectDetector(
    private val detectionMode: Int = CustomObjectDetectorOptions.SINGLE_IMAGE_MODE,
) {

    private val options: CustomObjectDetectorOptions
    private val detector: ObjectDetector

    init {
        val localModel = LocalModel.Builder()
            .setAssetFilePath("mobilenet_v1_1.0_224_quant.tflite")
            .build()

        options = CustomObjectDetectorOptions.Builder(localModel)
            .setDetectorMode(detectionMode)
            .enableClassification()
            .setClassificationConfidenceThreshold(0.5f)
            .setMaxPerObjectLabelCount(3)
            .build()

        detector = ObjectDetection.getClient(options)
    }

    fun processInputImage(
        image: InputImage,
        onResult: (objects: List<DetectedObject>, width: Int, height: Int) -> Unit
    ) {
        detector.process(image)
            .addOnSuccessListener { objects ->
                Log.d("WasteDetector", "Detected ${objects.size} objects")
                onResult(objects, image.width, image.height)
            }
            .addOnFailureListener { e ->
                Log.e("WasteDetector", "Detection failed", e)
            }
    }
}