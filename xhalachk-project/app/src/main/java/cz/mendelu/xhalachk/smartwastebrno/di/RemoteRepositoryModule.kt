package cz.mendelu.xhalachk.smartwastebrno.di




import cz.mendelu.xhalachk.smartwastebrno.communication.IWasteCollectionRemoteRepository
import cz.mendelu.xhalachk.smartwastebrno.communication.WasteCollectionAPI
import cz.mendelu.xhalachk.smartwastebrno.communication.WasteCollectionRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoryModule {

    @Provides
    @Singleton
    fun provideWasteCollectionRemoteRepository(petsAPI: WasteCollectionAPI): IWasteCollectionRemoteRepository {
        return WasteCollectionRemoteRepositoryImpl(petsAPI)
    }


}