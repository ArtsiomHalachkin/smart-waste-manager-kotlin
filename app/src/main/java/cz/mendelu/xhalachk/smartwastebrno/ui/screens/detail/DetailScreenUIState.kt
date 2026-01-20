package cz.mendelu.xhalachk.smartwastebrno.ui.screens.detail

import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection

data class DetailScreenUIState (
    val wasteCollection: WasteCollection? = null,
    val isLoading: Boolean = true
)