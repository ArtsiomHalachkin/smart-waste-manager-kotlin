package cz.mendelu.xhalachk.smartwastebrno.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import cz.mendelu.xhalachk.smartwastebrno.model.Location
import cz.mendelu.xhalachk.smartwastebrno.model.WasteCollection

class CustomMapRenderer(
    context: Context,
    googleMap: GoogleMap,
    clusterManager: ClusterManager<WasteCollection>
) : DefaultClusterRenderer<WasteCollection>(context, googleMap, clusterManager) {

    override fun shouldRenderAsCluster(cluster: Cluster<WasteCollection?>): Boolean {
        return cluster.size > 10
    }

    override fun onBeforeClusterItemRendered(item: WasteCollection, markerOptions: MarkerOptions) {

        val markerHue = when (item.wasteType) {
            "Sklo barevné", "Sklo" -> BitmapDescriptorFactory.HUE_GREEN

            "Papír" -> BitmapDescriptorFactory.HUE_BLUE

            "Plasty, nápojové kartony a hliníkové plechovky od nápojů" -> BitmapDescriptorFactory.HUE_YELLOW

            "Biologický odpad" -> BitmapDescriptorFactory.HUE_ORANGE

            else -> BitmapDescriptorFactory.HUE_RED
        }

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(markerHue))
        markerOptions.title(item.wasteType)

        super.onBeforeClusterItemRendered(item, markerOptions)
    }

}
