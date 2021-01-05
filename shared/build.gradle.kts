plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("kotlinx-serialization")
    kotlin("native.cocoapods")
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

    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

project.version = summerVersion

kotlin {
    android()
    js(IR) {
        // TODO does not work with current Kotlin 1.4.21 (will be fixed in 1.4.30)
        //      https://youtrack.jetbrains.com/issue/KT-41076#focus=Comments-27-4619722.0-0
        browser {
            webpackTask {
                output.libraryTarget = "umd2"
            }
            commonWebpackConfig {

            }
//            kotlinOptions.metaInfo = true
//            kotlinOptions.sourceMap = true
//            kotlinOptions.suppressWarnings = true
//            kotlinOptions.verbose = true
//            kotlinOptions.main = "call"
//            kotlinOptions.moduleKind = "umd"
        }
        binaries.library()
    }
    iosArm64()
    iosX64()

    cocoapods {
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        frameworkName = "shared"
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                implementation("io.ktor:ktor-client-core:$ktorVersion")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                implementation("com.github.adevone.summer:summer:$exampleSummerVersion")
                implementation("com.github.adevone.summer:summer-arch-lifecycle:$exampleSummerVersion")
//                implementation(project(":summer"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
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