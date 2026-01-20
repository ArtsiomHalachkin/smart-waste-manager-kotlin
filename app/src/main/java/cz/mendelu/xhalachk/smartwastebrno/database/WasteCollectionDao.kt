package cz.mendelu.xhalachk.smartwastebrno.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WasteCollectionDao {

    @Query("SELECT * FROM waste_collection")
    fun getAll(): Flow<List<WasteCollection>>

    @Query("SELECT * FROM waste_collection WHERE id = :id")
    suspend fun getById(id: Long): WasteCollection

    @Insert
    suspend fun insert(wasteCollection: WasteCollection)

    @Delete
    suspend fun delete(wasteCollection: WasteCollection)


}