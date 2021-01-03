import java.util.*

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("maven-publish")
}

android {
    compileSdkVersion(targetVersion)

    defaultConfig {
        minSdkVersion(minVersion)
        targetSdkVersion(targetVersion)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
        }
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
    iosArm64 {
        binaries {
            framework()
        }
    }
    iosArm32 {
        binaries {
            framework()
        }
    }
    iosX64 {
        binaries {
            framework()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("com.github.adevone.summer:summer:$summerVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("android.arch.lifecycle:viewmodel:$lifecycleVersion")
                implementation("android.arch.lifecycle:common:$lifecycleVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            }
        }
        val iosArm64Main by getting {
            dependencies {
            }
        }
        val iosX64Main by getting {
            dependsOn(iosArm64Main)
        }
        val iosArm32Main by getting {
            dependsOn(iosArm64Main)
        }
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
            maven("https://api.bintray.com/maven/summermpp/summer/${project.name}/;publish=0;override=1") {
                this.name = "bintray"

                credentials {
                    this.username = bintrayProps.getProperty("USERNAME")
                    this.password = bintrayProps.getProperty("API_KEY")
                }
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.6"
    }
}