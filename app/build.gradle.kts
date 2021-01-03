plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    compileSdkVersion(targetVersion)

    defaultConfig {
        applicationId = "io.adev.summer.example"
        minSdkVersion(minVersion)
        targetSdkVersion(targetVersion)
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_6
        sourceCompatibility = JavaVersion.VERSION_1_6
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

    implementation("org.kodein.di:kodein-di:$kodeinVersion")
    implementation("com.russhwolf:multiplatform-settings:$multiplatformSettingVersion")

    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("ru.terrakok.cicerone:cicerone:5.1.1")

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.fragment:fragment-ktx:1.2.5")

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
    }
}