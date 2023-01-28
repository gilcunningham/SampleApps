plugins {
    //id("com.android.application")
    id("com.android.application")
    //id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
}

android {
    namespace = "com.gil.sample"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.gil.sample"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        //jvmTarget = '11'
        // userIR = true
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // RX
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.0")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")

    //implementation "io.reactivex.rxjava3:rxjava:3.0.4"
    //implementation "io.reactivex.rxjava3:rxandroid:3.0.0"

    //implementation("io.reactivex.rxjava2:rxjava:2.2.0")
    //implementation("io.reactivex.rxjava2:rxandroid:2.0.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Compose
    implementation("androidx.compose.runtime:runtime:1.3.3")
    implementation("androidx.compose.runtime:runtime-livedata:1.3.3")
    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.ui:ui-tooling:1.3.3")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.foundation:foundation-layout:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    //implementation("com.google.android.material:compose-theme-adapter:1.3.1")

    // tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}