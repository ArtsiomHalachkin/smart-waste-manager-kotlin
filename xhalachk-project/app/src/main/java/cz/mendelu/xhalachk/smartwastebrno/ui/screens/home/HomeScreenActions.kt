package cz.mendelu.xhalachk.smartwastebrno.ui.screens.home

import android.net.Uri
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection

interface HomeScreenActions {
    fun onShowSheet()
    fun onHideSheet()
    fun onDeleteItem(item: WasteCollection)

}
