package cz.mendelu.xhalachk.smartwastebrno.di

import android.content.Context
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollectionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WasteCollectionDatabase {
        return WasteCollectionDatabase.getDatabase(context)
    }
}
