package cz.mendelu.xhalachk.smartwastebrno.ui.screens.map

import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollection

data class MapScreenUIState(
    var latitude: Double = 49.20668381260559,
    var longitude: Double = 16.60843050166754,
    var locationChanged: Boolean = false,

    var filter: String? = null,
    var collections: List<WasteCollection> = emptyList(),

    var selectedCollection: WasteCollection? = null,

    var imageUri: String? = null,

    var error: MapScreenError? = null,
    var loading: Boolean = true,

    val isWasteSaved: Boolean = false
)