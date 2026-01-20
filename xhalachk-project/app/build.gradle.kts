

import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").reader())

val server = properties.getProperty("server")

android {

    val versionMajor = 0
    val versionMinor = 0
    val versionPatch = 1
    val myVersionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    val myVersionName = "${versionMajor}.${versionMinor}.${versionPatch}"


    namespace = "cz.mendelu.xhalachk.smartwastebrno"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "cz.mendelu.xhalachk.smartwastebrno"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "cz.mendelu.xhalachk.smartwastebrno.MyHiltTestRunner"
    }
    androidResources {
        noCompress("tflite")

    }




    buildTypes {
        release {
            setFlavorDimensions(listOf("version"))
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField(type = "String", name = "SERVER_URL", value = server)
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    aaptOptions {
        noCompress  += "tflite"
        ignoreAssetsPattern = "!.svn:!.git:!.ds_store:!*.scc:.*:!CVS:!thumbs.db:!picasa.ini:!*~"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.objectdetection)
    implementation(libs.image.labeling.custom.common)
    implementation(libs.image.labeling.default.common)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    kapt(libs.hilt.kapt)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.retrofit.okhtt3)
    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    // DataStore
    implementation(libs.datastore.core)
    implementation(libs.datastore.prefs)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ktx)
    implementation(libs.navigation.compose)

    // Map
    implementation(libs.googlemap)
    implementation(libs.googlemap.compose)
    implementation(libs.googlemap.foundation)
    implementation(libs.googlemap.utils)
    implementation(libs.googlemap.widgets)
    implementation(libs.googlemap.compose.utils)

    implementation(libs.lifecycle)
    implementation(libs.room.ktx)
    implementation(libs.room.viewmodel)
    implementation(libs.room.lifecycle)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler.kapt)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation(libs.androidx.material.icons.extended)

    // Coil
    implementation(libs.coil.compose)

    // Timber
    implementation(libs.timber)

    // Tests
    testImplementation(libs.junit)
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Hilt testing
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)


    implementation(libs.camera.core)
    implementation(libs.camera.view)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecyvle)
    implementation(libs.camera.extensions)
    implementation(libs.camera.video)

    implementation(libs.objectdetection.custom)
    implementation(libs.tflite)

    implementation(libs.linkfirebase)

    implementation(libs.image.labeling)
    implementation(libs.image.labeling.custom)
    implementation(libs.image.odml)
    implementation(libs.appintro)
    implementation(libs.androidx.core.splashscreen)

    implementation("io.github.jan-tennert.supabase:storage-kt:2.1.3")
    implementation("io.ktor:ktor-client-android:2.3.7")
}
