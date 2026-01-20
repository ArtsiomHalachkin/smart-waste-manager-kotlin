package cz.mendelu.xhalachk.smartwastebrno.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.Json


data class WasteCollection(
    val id: Long,

    @Json(name = "nazev")
    val name: String,
    @Json(name = "komodita_odpad_separovany")
    val wasteType: String,

    val volume: Int,

    val latitude: Double,
    val longitude: Double
): ClusterItem {
    override fun getPosition(): LatLng {
        return LatLng(latitude, longitude)
    }

    override fun getTitle(): String? {
        return name
    }

    override fun getSnippet(): String? {
        return wasteType
    }

    override fun getZIndex(): Float? {
        return 0.0f
    }


}

