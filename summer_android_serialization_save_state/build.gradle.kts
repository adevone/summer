import com.jfrog.bintray.gradle.BintrayExtension
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
    id("maven")
    id("maven-publish")
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
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation("androidx.appcompat:appcompat:1.2.0")

    implementation("com.github.adevone.summer:summer:$summerVersion")
    implementation("com.github.adevone.summer:summer-androidx:$summerVersion")
    implementation(project(":summer-serialization-strategy"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("dev.ahmedmourad.bundlizer:bundlizer:0.1.0")
}

val sourceJar by tasks.registering(Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.set("sources")
}

group = summerGroup
version = summerVersion

publishing {
    publications {
        create<MavenPublication>("summerAndroidXSaveState") {
            groupId = project.group.toString()
            artifactId = project.name
            version = summerVersion
            artifact(tasks.getByName("sourceJar"))
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
        }
    }
}

val propsFile = File(rootProject.rootDir, "bintray.properties")
if (propsFile.exists()) {
    bintray {
        val bintrayProps = Properties().apply {
            load(propsFile.inputStream())
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
        setPublications("summerAndroidXSaveState")
    }
}