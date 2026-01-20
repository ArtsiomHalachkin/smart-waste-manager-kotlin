package cz.mendelu.xhalachk.smartwastebrno.database

import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollection
import cz.mendelu.xhalachk.smartwastebrno.mock.DatabaseMock.getDatabaseMock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FakeWasteCollectionLocalRepositoryImpl @Inject constructor() : IWasteCollectionLocalRepository {

    private val dbItems = MutableStateFlow<List<WasteCollection>>(emptyList())


    override fun getAllWasteCollections(): Flow<List<WasteCollection>> {
        return dbItems
    }

    override suspend fun insertWasteCollection(waste: WasteCollection) {
        dbItems.update { currentList ->
            val mutableList = currentList.toMutableList()
            mutableList.add(waste)
            mutableList
        }
    }

    override suspend fun deleteWasteCollection(waste: WasteCollection) {
        dbItems.update { currentList ->
            currentList.filter { it.id != waste.id }
        }
    }

    override suspend fun getWasteCollectionById(id: Long): WasteCollection {
        return dbItems.value.find { it.id == id }
            ?: throw IllegalStateException("Item with id $id not found in Fake DB")
    }

    override suspend fun uploadImage(bytes: ByteArray, fileName: String): Boolean {
        return true
    }



    override fun getPublicUrl(fileName: String): String {
        return "https://fake.supabase.co/storage/v1/object/public/waste/$fileName"
    }
}