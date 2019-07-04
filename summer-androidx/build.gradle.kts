import com.android.build.gradle.ProguardFiles.getDefaultProguardFile

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("maven")
    id("maven-publish")
    id("kotlinx-atomicfu")
}

//val libraryGroupId = "ru.napoleonit"
//val libraryArtifactId = "summer-android"
//val libraryVersion = rootProject.ext.summer_version
//val libraryFileName = "summer-android-release.aar"

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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")

    implementation("androidx.appcompat:appcompat:1.0.2")

    implementation("summer:summer:$summerVersion")
}

val sourceJar by tasks.registering(Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs)
}

publishing {
    publications {
        create<MavenPublication>("summerAndroidX") {
            groupId = "summer"
            artifactId = "summer-androidx"
            version = summerVersion
//            artifact(tasks.named("sourceJar"))
            artifact("$buildDir/outputs/aar/summer-androidx-release.aar")
        }
    }
    repositories {
        maven {
            url = uri(project.extra["REPO_URL"].toString() + "/" + project.extra["REPO_KEY"].toString())
            credentials {
                username = project.extra["REPO_USERNAME"].toString()
                password = project.extra["REPO_ENCRYPTED_PASSWORD"].toString()
            }
        }
    }
}