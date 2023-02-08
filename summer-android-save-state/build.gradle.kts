plugins {
    id("com.android.library")
    id("kotlin-android")
    id("convention.publication.android")
}

android {
    compileSdk = targetVersion

    defaultConfig {
        minSdk = 21 // Some Bundle methods requires 21 version
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

    implementation(project(":summer"))
    implementation(project(":summer-androidx"))
}

group = summerGroup
version = summerVersion

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.6"
    }
}