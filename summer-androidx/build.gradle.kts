plugins {
    id("com.android.library")
    id("kotlin-android")
    id("convention.publication.android")
}

android {
    compileSdk = targetVersion

    defaultConfig {
        minSdk = 14
        targetSdk = targetVersion
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

    api("androidx.appcompat:appcompat:$appCompatVersion")

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