plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
   // id("com.google.dagger.hilt.android")
}

android {
    namespace = "dev.ashish.talkie"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.ashish.talkie"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Retrofit depenedency
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //Glide
    implementation(libs.glide)

    // hilt
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.browser)

    //Room
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    kapt (libs.androidx.room.compiler)

    //Paging
    implementation(libs.androidx.paging.runtime)

    //lottie
    implementation(libs.lottie)
    implementation(libs.paper.onboarding)

    //dagger2 Dependency
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
}