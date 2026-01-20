package cz.mendelu.xhalachk.smartwastebrno.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "latitude")
    var latitude: Double?,
    @Json(name = "longitude")
    var longitude: Double?
)