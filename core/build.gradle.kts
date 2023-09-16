plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0

android {
    namespace = "ir.pooriak.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

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
            buildConfigField(
                "int",
                "appVersion",
                "${versionMajor * 10000 + versionMinor * 100 + versionPatch}"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://run.mocky.io/v3/\""
            )
        }

        debug {
            isMinifyEnabled = false
            buildConfigField(
                "int",
                "appVersion",
                "${versionMajor * 10000 + versionMinor * 100 + versionPatch}"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://run.mocky.io/v3/\""
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    // Base Android Dependencies
    api("androidx.core:core-ktx:1.12.0")
    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.9.0")
    api("androidx.paging:paging-common-ktx:3.2.1")

    // Test Tools
    testApi("junit:junit:4.13.2")
    androidTestApi("androidx.test.ext:junit:1.1.5")
    androidTestApi("androidx.test.espresso:espresso-core:3.5.1")

    // Navigation Component
    val nav_version = "2.7.2"
    api("androidx.navigation:navigation-fragment-ktx:$nav_version")
    api("androidx.navigation:navigation-ui-ktx:$nav_version")

    //  Koin Dependency Injection
    val koin_version = "3.5.0"
//    api("io.insert-koin:koin-core:$koin_version")
    api("io.insert-koin:koin-android:$koin_version")


    //  Retrofit Network
    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    api("com.squareup.okhttp3:okhttp:4.9.0")
    api("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //  Kotlin Coroutines
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // ROOM DB
    val room_version = "2.5.2"
    api("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    api("net.zetetic:android-database-sqlcipher:4.5.3")
    api("androidx.sqlite:sqlite:2.1.0")

    // RX Java & RX Android
    api("io.reactivex.rxjava3:rxandroid:3.0.2")
    api("io.reactivex.rxjava3:rxjava:3.1.5")
    api("androidx.room:room-rxjava3:2.5.2")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}