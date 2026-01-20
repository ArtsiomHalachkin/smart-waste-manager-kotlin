package cz.mendelu.xhalachk.smartwastebrno.di

import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollectionDao
import cz.mendelu.xhalachk.smartwastebrno.database.WasteCollectionLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dao: WasteCollectionDao, supabaseClient: SupabaseClient): IWasteCollectionLocalRepository {
        return WasteCollectionLocalRepositoryImpl(
            wasteCollectionDao = dao,
            supabaseClient = supabaseClient
        )
    }
}
