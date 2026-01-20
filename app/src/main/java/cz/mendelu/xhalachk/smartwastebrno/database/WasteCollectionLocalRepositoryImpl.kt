package cz.mendelu.xhalachk.smartwastebrno.database

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WasteCollectionLocalRepositoryImpl
@Inject constructor (
    private val wasteCollectionDao: WasteCollectionDao,
    private val supabaseClient: SupabaseClient,
)
    : IWasteCollectionLocalRepository
{
    override suspend fun insertWasteCollection(wasteCollection: WasteCollection) {
        wasteCollectionDao.insert(wasteCollection)
    }

    override suspend fun deleteWasteCollection(wasteCollection: WasteCollection) {
        wasteCollectionDao.delete(wasteCollection)
    }

    override fun getAllWasteCollections(): Flow<List<WasteCollection>> {
        return wasteCollectionDao.getAll()
    }

    override suspend fun getWasteCollectionById(id: Long): WasteCollection {
        return wasteCollectionDao.getById(id)

    }

    override suspend fun uploadImage(
        bytes: ByteArray,
        fileName: String
    ): Boolean {
        return try {
            val bucket = supabaseClient.storage.from("waste")
            bucket.upload(fileName, bytes, upsert = false)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun getPublicUrl(fileName: String): String {
        // Construct the URL manually or use the SDK helper if available
        // Format: https://<PROJECT_ID>.supabase.co/storage/v1/object/public/<BUCKET>/<FILENAME>
        return supabaseClient.storage.from("waste").publicUrl(fileName)
    }
}