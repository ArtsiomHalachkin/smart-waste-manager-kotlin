package cz.mendelu.xhalachk.smartwastebrno.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WasteCollection::class], version = 6, exportSchema = false)
abstract class WasteCollectionDatabase   : RoomDatabase() {

    abstract fun wasteCollectionDao(): WasteCollectionDao

    companion object {
        private var INSTANCE: WasteCollectionDatabase? = null
        fun getDatabase(context: Context): WasteCollectionDatabase {
            if (INSTANCE == null) {
                synchronized(WasteCollectionDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WasteCollectionDatabase::class.java,
                            "waste_collection_database"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }

    }
}
