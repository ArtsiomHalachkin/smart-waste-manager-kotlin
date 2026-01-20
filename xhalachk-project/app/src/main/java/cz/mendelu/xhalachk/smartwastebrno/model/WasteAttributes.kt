package cz.mendelu.xhalachk.smartwastebrno.model

import com.squareup.moshi.Json

data class WasteAttributes(
    @Json(name = "ObjectId")
    val id: Long,

    @Json(name = "nazev")
    val name: String?,

    @Json(name = "komodita_odpad_separovany")
    val wasteCommodity: String?,

    @Json(name = "objem")
    val volume: Int?
)
