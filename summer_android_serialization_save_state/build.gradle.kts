plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("convention.publication.android")
}

android {
    compileSdk = targetVersion

    defaultConfig {
        // bundlizer requires minSdk >= 16
        minSdk = 16
        targetSdk = targetVersion
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    api("androidx.appcompat:appcompat:$appCompatVersion")

    api("com.github.adevone.summer:summer:$summerVersion")
    api("com.github.adevone.summer:summer-androidx:$summerVersion")
    api("com.github.adevone.summer:summer-serialization-strategy:$summerVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("dev.ahmedmourad.bundlizer:bundlizer-core:0.3.0")
}

group = summerGroup
version = summerVersion