package cz.mendelu.xhalachk.smartwastebrno.database

import kotlinx.coroutines.flow.Flow

interface IWasteCollectionLocalRepository  {
    suspend fun insertWasteCollection(wasteCollection: WasteCollection)
    suspend fun deleteWasteCollection(wasteCollection: WasteCollection)
    fun getAllWasteCollections(): Flow<List<WasteCollection>>
    suspend fun getWasteCollectionById(id: Long): WasteCollection

    suspend fun uploadImage(bytes: ByteArray, fileName: String): Boolean
    fun getPublicUrl(fileName: String): String

}