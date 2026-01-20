package cz.mendelu.xhalachk.smartwastebrno.di


import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollectionDao
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollectionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideDao(database: WasteCollectionDatabase): WasteCollectionDao {
        return database.wasteCollectionDao()
    }
}
