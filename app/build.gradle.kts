import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties
import java.util.TimeZone

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
    id("kotlin-parcelize")
}

val buildProperties = Properties()
val buildPropertiesFile = rootProject.file("build.properties")
if (buildPropertiesFile.exists()) {
    buildProperties.load(FileInputStream(buildPropertiesFile))
}
val appVersionCode = buildProperties.getProperty("versionCode", "1").toInt()
val appVersionName: String? = buildProperties.getProperty("versionName", "1.0.0")
val appProductName: String? = buildProperties.getProperty("productName", "arkhe")
val now: Instant? = Clock.systemUTC().instant()
val localDateTime: LocalDateTime? = now?.atZone(TimeZone.getDefault().toZoneId())?.toLocalDateTime()
val timestampFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("yyMMddHHmm")
val timestampString = localDateTime?.format(timestampFormatter)
val buildTimestamp = timestampString

android {
    namespace = "com.arkhe.base"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.arkhe.base"
        minSdk = 23
        targetSdk = 36
        versionCode = appVersionCode
        versionName = "$appVersionName.build$buildTimestamp"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        multiDexEnabled = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

base {
    archivesName.set("$appProductName-$appVersionName.build$buildTimestamp")
}

dependencies {
    // Compose BOM - Platform for version alignment
    implementation(platform(libs.androidx.compose.bom))

    // AndroidX Core Libraries
    implementation(libs.bundles.androidx.core)

    // Material Design & Icons
    implementation(libs.bundles.material.design)

    // Lifecycle & Architecture Components
    implementation(libs.bundles.lifecycle)

    // Compose UI Components
    implementation(libs.bundles.compose.core)

    // Navigation
    implementation(libs.bundles.navigation)

    // Async Programming - Coroutines
    implementation(libs.bundles.coroutines)

    // Serialization
    implementation(libs.bundles.serialization)

    // Networking - Ktor Client
    implementation(libs.bundles.ktor.client)

    // Dependency Injection - Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Database - Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // Data Storage - DataStore
    implementation(libs.bundles.datastore)

    // Barcode & QR Code Generation
    implementation(libs.bundles.zxing)

    // Unit Testing
    testImplementation(libs.bundles.testing.unit)

    // Android Instrumented Testing
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.bundles.compose.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Debug Tools
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}