package cz.mendelu.xhalachk.smartwastebrno.model

data class WasteApiResponse(
    val features: List<WasteFeature>,

    val exceededTransferLimit: Boolean
)
