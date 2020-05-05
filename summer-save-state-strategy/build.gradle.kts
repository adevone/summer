import java.util.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
}

android {
    compileSdkVersion(targetVersion)

    defaultConfig {
        minSdkVersion(minVersion)
        targetSdkVersion(targetVersion)
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

kotlin {
    android()
    iosArm64 {
        binaries {
            framework()
        }
        compilations.forEach { compilation ->
            compilation.kotlinOptions.freeCompilerArgs += "-Xobjc-generics"
        }
    }
    iosArm32 {
        binaries {
            framework()
        }
        compilations.forEach { compilation ->
            compilation.kotlinOptions.freeCompilerArgs += "-Xobjc-generics"
        }
    }
    iosX64 {
        binaries {
            framework()
        }
        compilations.forEach { compilation ->
            compilation.kotlinOptions.freeCompilerArgs += "-Xobjc-generics"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")
                implementation("com.github.adevone.summer:summer:$summerVersion")
//                implementation(project(":summer"))
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("com.github.adevone.summer:summer-androidx:$summerVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
                implementation("dev.ahmedmourad.bundlizer:bundlizer:0.1.0")
                implementation("androidx.appcompat:appcompat:1.1.0")
            }
        }
        getByName("iosArm64Main") {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationVersion")
            }
        }
        getByName("iosX64Main").dependsOn(getByName("iosArm64Main"))
    }
}

group = summerGroup
version = summerVersion

val propsFile = File(rootProject.rootDir, "bintray.properties")
if (propsFile.exists()) {
    publishing {
        val bintrayProps = Properties().apply {
            load(propsFile.inputStream())
        }
        repositories {
            maven("https://api.bintray.com/maven/summermpp/summer/summer/;publish=0") {
                name = "bintray"

                credentials {
                    username = bintrayProps.getProperty("USERNAME")
                    password = bintrayProps.getProperty("API_KEY")
                }
            }
        }
    }
}