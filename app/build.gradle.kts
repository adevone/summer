plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlinx-serialization")
}

androidExtensions {
    isExperimental = true
}

android {
    compileSdkVersion(targetVersion)

    defaultConfig {
        applicationId = "ru.napoleonit"
        minSdkVersion(minVersion)
        targetSdkVersion(targetVersion)
        multiDexEnabled = true
        resConfigs("en", "ru")
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
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

    packagingOptions {
        exclude("META-INF/proguard/androidx-annotations.pro")
        exclude("META-INF/*.version")
        exclude("META-INF/*.kotlin_module")
        exclude("**.kotlin_builtins")
        exclude("**.kotlin_metadata")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
    }
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion") {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
    }

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")

    implementation("org.kodein.di:kodein-di-erased-jvm:$kodeinVersion")
    implementation("com.russhwolf:multiplatform-settings:$multiplatformSettingVersion")

    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("ru.terrakok.cicerone:cicerone:5.1.0")

    implementation("androidx.multidex:multidex:2.0.1")

    implementation("androidx.core:core-ktx:1.2.0")

    implementation(project(":shared"))

    implementation("com.github.adevone.summer:summer:$summerVersion")
    implementation("com.github.adevone.summer:summer-androidx:$summerVersion")
//    implementation(project(":summer"))
//    implementation(project(":summer-androidx"))

    implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}