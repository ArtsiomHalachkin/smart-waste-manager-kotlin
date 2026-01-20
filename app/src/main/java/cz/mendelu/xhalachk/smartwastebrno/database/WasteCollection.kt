package cz.mendelu.xhalachk.smartwastebrno.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waste_collection")
data class WasteCollection(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val type: String,
    var date: Long? = null,
    val imageUrl: String
)