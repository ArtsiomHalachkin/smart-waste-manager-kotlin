package cz.mendelu.xhalachk.smartwastebrno.model

import com.squareup.moshi.Json

data class WasteGeometry(
    @Json(name = "x")
    val longitude: Double,
    @Json(name = "y")
    val latitude: Double
)
