plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("kotlinx-serialization")
    kotlin("native.cocoapods")
    id("kotlin-kapt")
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

project.version = summerVersion

kotlin {
    android()
//    jvm()
    iosArm64()
    iosX64()

    cocoapods {
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        frameworkName = "shared"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                implementation("org.kodein.di:kodein-di:$kodeinVersion")
                implementation("com.russhwolf:multiplatform-settings:$multiplatformSettingVersion")
                implementation("io.ktor:ktor-client-core:$ktorVersion")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                implementation("com.github.adevone.summer:summer:$summerVersion")
                implementation("com.github.adevone.summer:summer-arch-lifecycle:$summerVersion")
//                implementation(project(":summer"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
//        val jvmMain by getting {
//            dependencies {
//                implementation(kotlin("stdlib"))
//                implementation(kotlin("reflect"))
//            }
//        }
//        val jvmTest by getting {
//            dependencies {
//                implementation(kotlin("test"))
//                implementation(kotlin("test-junit"))
//            }
//        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("reflect"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val iosArm64Main by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
        val iosX64Main by getting {
            dependsOn(iosArm64Main)
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}