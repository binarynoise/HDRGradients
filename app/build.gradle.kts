plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "de.binarynoise.hdrgradients"
    compileSdk = 37
    
    defaultConfig {
        applicationId = "de.binarynoise.hdrgradients"
        minSdk = 34
        targetSdk = 37
        versionCode = 3
        versionName = "3"
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    packaging {
        resources {
            excludes.addAll(
                arrayOf(
                    "/kotlin/**/*",
                    "/kotlin/*",
                    "/META-INF/**/*",
                    "/META-INF/*",
                )
            )
        }
    }
    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.hiddenapibypass)
}
