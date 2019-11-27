import com.jfrog.bintray.gradle.BintrayExtension
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven")
    id("maven-publish")
    id("kotlinx-atomicfu")
    id("com.jfrog.bintray")
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

    implementation("com.github.adevone.summer:summer:$summerVersion")
//    implementation(project(":summer"))
}

val sourceJar by tasks.registering(Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.set("sources")
}

group = "com.github.adevone.summer"
version = summerVersion

publishing {
    publications {
        create<MavenPublication>("summerAndroidX") {
            groupId = project.group.toString()
            artifactId = project.name
            version = summerVersion
            artifact(tasks.getByName("sourceJar"))
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
        }
    }
}

bintray {
    val propsStream = File(rootProject.rootDir, "bintray.properties").inputStream()
    val bintrayProps = Properties().apply {
        load(propsStream)
    }
    user = bintrayProps.getProperty("USERNAME")
    key = bintrayProps.getProperty("API_KEY")
    pkg(closureOf<BintrayExtension.PackageConfig> {
        repo = "summer"
        name = project.name
        userOrg = "summermpp"
        setLicenses("MIT")
        vcsUrl = "https://github.com/adevone/summer"
    })
    setPublications("summerAndroidX")
}