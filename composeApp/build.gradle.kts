plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
}

android {
    compileSdkVersion(targetVersion)

    defaultConfig {
        applicationId = "io.adev.summer.example.compose"
        minSdkVersion(minVersion)
        targetSdkVersion(targetVersion)
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_6
        sourceCompatibility = JavaVersion.VERSION_1_6
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }

    composeOptions {
        kotlinCompilerVersion = kotlinVersion
        kotlinCompilerExtensionVersion = composeVersion
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isDebuggable = true
            isTestCoverageEnabled = false
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")

    implementation("androidx.appcompat:appcompat:$appCompatVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.navigation:navigation-compose:1.0.0-alpha04")

    implementation("com.github.bumptech.glide:glide:4.11.0")

    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    implementation("org.kodein.di:kodein-di:$kodeinVersion")
    implementation("com.russhwolf:multiplatform-settings:$multiplatformSettingVersion")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    implementation(project(":shared"))

    implementation("com.github.adevone.summer:summer:$exampleSummerVersion")
    implementation("com.github.adevone.summer:summer-arch-lifecycle:$exampleSummerVersion")
//    implementation(project(":summer"))
//    implementation(project(":summer-arch-lifecycle"))

    implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-Xallow-jvm-ir-dependencies",
            "-Xskip-prerelease-check"
        )
    }
}