import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

//setup env / load env
val envProps = Properties()
val envFile = File(rootDir, ".env")
if (envFile.exists()) {
    envProps.load(FileInputStream(envFile))
}

// Daftar key yang akan di-inject ke BuildConfig
val autoEnvKeys = listOf(
    "BASE_URL",
    "API_KEY",
    "NEWS_API_KEY"
)

android {
    namespace = "com.project.phistingdetection"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.project.phistingdetection"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        autoEnvKeys.forEach { key ->
            val value = envProps[key] ?: error("Missing `$key` in .env")
            buildConfigField("String", key, "\"$value\"")
        }
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation and Animation
    implementation("androidx.navigation:navigation-compose:2.7.3")
    implementation("androidx.compose.animation:animation:1.5.0")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Coil
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Icons Extended
    implementation("androidx.compose.material:material-icons-extended")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation ("io.coil-kt:coil-compose:1.4.0")
    implementation ("androidx.compose.ui:ui-tooling:1.0.0-beta01")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    // Accompanist refresh page
    implementation("com.google.accompanist:accompanist-swiperefresh:0.30.1")
}