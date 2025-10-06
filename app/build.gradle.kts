plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.servicetest"
    compileSdk = 35
    ndkVersion = "26.1.10909125"

    packagingOptions.jniLibs.useLegacyPackaging = true

    lint {
        abortOnError = false
    }

    androidResources {
        noCompress += "rcc"
    }

    defaultConfig {
        applicationId = "com.servicetest"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.add("arm64-v8a")
        }

        externalNativeBuild {
            cmake {
                cppFlags += ""
                arguments += "-DANDROID_STL=c++_shared"
                arguments += "QT_ANDROID_EXTRA_LIBS=libcrypto.so;libssl.so"
            }
        }
    }
    externalNativeBuild {
        cmake {
//            path("src/main/cpp/CMakeLists.txt")
        }
    }

    sourceSets.getByName("main") {
        jniLibs.srcDirs("lib")
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
    buildFeatures {
        viewBinding = true
        prefab = true
    }
}

dependencies {
    implementation(fileTree("lib/arm64-v8a") { include("*.jar", "*.aar") })
    implementation(fileTree("lib") { include("*.jar", "*.aar") })
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
