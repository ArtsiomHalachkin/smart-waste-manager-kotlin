package cz.mendelu.xhalachk.smartwastebrno.ui.screens.home

import android.net.Uri
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.model.SortingItem

data class HomeScreenUIState(
    val loading: Boolean = false,
    val sortingHistory: List<WasteCollection> = emptyList(),
    val showSheet: Boolean = false,

    )
