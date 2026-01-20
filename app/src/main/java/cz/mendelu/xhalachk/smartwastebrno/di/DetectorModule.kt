package cz.mendelu.xhalachk.smartwastebrno.di


import com.google.mlkit.vision.objects.custom.CustomObjectDetectorOptions
import cz.mendelu.xhalachk.smartwastebrno.analyzers.CustomModelObjectDetector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetectorModule {

    @Provides
    @Singleton
    fun provideCustomDetector(): CustomModelObjectDetector {
        return CustomModelObjectDetector(
            detectionMode = CustomObjectDetectorOptions.SINGLE_IMAGE_MODE
        )
    }
}