package cz.mendelu.xhalachk.smartwastebrno.mock

import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.model.WasteApiResponse
import cz.mendelu.xhalachk.smartwastebrno.model.WasteAttributes
import cz.mendelu.xhalachk.smartwastebrno.model.WasteFeature
import cz.mendelu.xhalachk.smartwastebrno.model.WasteGeometry

object APIMock {


    val attr1 = WasteAttributes(
        id = 1L,
        name = "Test Bin 1",
        wasteCommodity = "Plast",
        volume = 100
    )

    val geo1 = WasteGeometry(
        latitude = 49.195060,
        longitude = 16.606837
    )

    val feature1 = WasteFeature(
        attributes = attr1,
        geometry = geo1
    )


    fun getWasteCollectionMockResponse() = WasteApiResponse(
        features = listOf(feature1),
        exceededTransferLimit = false
    )

}