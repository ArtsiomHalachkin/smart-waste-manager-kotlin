package cz.mendelu.xhalachk.smartwastebrno.di

import cz.mendelu.xhalachk.smartwastebrno.database.FakeWasteCollectionLocalRepositoryImpl
import cz.mendelu.xhalachk.smartwastebrno.database.IWasteCollectionLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
abstract class  FakeWasteCollectionLocalRepository {
    @Binds
    abstract fun provideFakeWasteCollectionLocalRepository(
        service: FakeWasteCollectionLocalRepositoryImpl
    ): IWasteCollectionLocalRepository
}

