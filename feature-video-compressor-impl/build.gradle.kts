plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.feature_video_compressor_impl"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // timber
    implementation(libs.timber)
    //media
    implementation(libs.media3.common)
    implementation(libs.media3.effect)
    implementation(libs.media3.transformer)
    // Hilt DI (Dependency Injection) + интеграция с Compose + KSP компилятор
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(project(":feature-video-compressor-api"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}