plugins {
    id("com.android.library")
    id("kotlin-android")
    id("convention.publication.android")
}

android {
    compileSdk = 28

    defaultConfig {
        minSdk = 14
        targetSdk = 28
    }

    lint {
        isAbortOnError = false
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    api("com.android.support:appcompat-v7:28.0.0")

    api("com.github.adevone.summer:summer:$summerVersion")
//    implementation(project(":summer"))
}

group = summerGroup
version = summerVersion

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.6"
    }
}