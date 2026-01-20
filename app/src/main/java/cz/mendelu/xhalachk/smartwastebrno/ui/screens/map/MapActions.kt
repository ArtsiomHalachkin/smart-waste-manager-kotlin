package cz.mendelu.xhalachk.smartwastebrno.ui.screens.map

import android.net.Uri
import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollection


interface MapActions {
    fun locationChanged(latitude: Double, longitude: Double)
    fun wasteTypeSelected(wasteType: String)
    fun onMarkerClick(collection: WasteCollection): Boolean
    fun onDismissSheet()
    fun setIncomingImageUri(uriString: String?)
    fun onSortClicked()
}
