package cz.mendelu.xhalachk.smartwastebrno.navigation

import kotlinx.serialization.Serializable
@Serializable
data class MapScreenDestination(
    var latitude: Double? = null,
    var longitude: Double? = null,
    var wasteType: String? = null,
    val imageUri: String? = null
)

@Serializable
object HomeScreenDestination

@Serializable
object PhotoPickerScreenDestination

@Serializable
object CategoryScreenDestination

@Serializable
data class DetailScreenDestination(var id: Long? = null)

@Serializable
data class DetectionScreenDestination(
    val imageUri: String? = null
)