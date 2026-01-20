package cz.mendelu.xhalachk.smartwastebrno.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://ciqunnqhaeizhtnzajxa.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNpcXVubnFoYWVpemh0bnphanhhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ5Mjg3ODQsImV4cCI6MjA4MDUwNDc4NH0.isxwEuKtV3hCRCKKD1CUEqBctSN-Y6h_aO9Q2qxPxMI"
        ){
            install(Storage)
        }
    }

}
