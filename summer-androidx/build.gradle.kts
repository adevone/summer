plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven")
    id("maven-publish")
    id("kotlinx-atomicfu")
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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")

    implementation("androidx.appcompat:appcompat:1.1.0")

//    implementation("summer:summer:$summerVersion")
    implementation(project(":summer"))
}

val sourceJar by tasks.registering(Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.set("sources")
}

group = "com.github.adevone"
version = summerVersion

publishing {
    publications {
        create<MavenPublication>("summerAndroidX") {
            groupId = "summer"
            artifactId = "summer-androidx"
            version = summerVersion
            artifact(tasks.getByName("sourceJar"))
            artifact("$buildDir/outputs/aar/summer-androidx-release.aar")
        }
    }
}