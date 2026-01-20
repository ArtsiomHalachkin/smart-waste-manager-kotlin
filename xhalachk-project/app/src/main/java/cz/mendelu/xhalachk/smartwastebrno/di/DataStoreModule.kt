package cz.mendelu.xhalachk.smartwastebrno.di


import android.content.Context

import cz.mendelu.xhalachk.smartwastebrno.datastore.DataStoreRepositoryImpl
import cz.mendelu.xhalachk.smartwastebrno.datastore.IDataStoreRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext appContext: Context): IDataStoreRepository
            = DataStoreRepositoryImpl(appContext)
}

