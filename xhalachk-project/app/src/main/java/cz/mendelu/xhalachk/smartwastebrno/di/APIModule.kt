package cz.mendelu.xhalachk.smartwastebrno.di



import cz.mendelu.xhalachk.smartwastebrno.communication.WasteCollectionAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Provides
    @Singleton
    fun provideWasteCollectionAPI(retrofit: Retrofit): WasteCollectionAPI {
        return retrofit.create(WasteCollectionAPI::class.java)
    }
}