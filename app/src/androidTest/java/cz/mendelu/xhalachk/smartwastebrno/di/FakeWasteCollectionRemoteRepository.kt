package cz.mendelu.xhalachk.smartwastebrno.di

import cz.mendelu.xhalachk.smartwastebrno.communication.FakeWasteCollectionRemoteRepositoryImpl
import cz.mendelu.xhalachk.smartwastebrno.communication.IWasteCollectionRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RemoteRepositoryModule::class],
)
abstract class FakeWasteCollectionRemoteRepository {
    @Binds
    abstract fun provideFakeWasteCollectionRemoteRepository(
        service: FakeWasteCollectionRemoteRepositoryImpl
    ): IWasteCollectionRemoteRepository

}