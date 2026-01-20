package cz.mendelu.xhalachk.smartwastebrno.model


// This is a new helper class for your repository to return.
// It bundles the list of collections AND the pagination info.
data class WasteCollectionResponse(
    val collections: List<WasteCollection>,
    val hasMoreData: Boolean
)