import com.jfrog.bintray.gradle.BintrayExtension
import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven")
    id("maven-publish")
    id("com.jfrog.bintray")
}

android {
    compileSdk = 28

    defaultConfig {
        minSdk = 14
        targetSdk = 28
    }

    lint {
        isAbortOnError = false
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

    api("com.android.support:appcompat-v7:28.0.0")

    api("com.github.adevone.summer:summer:$summerVersion")
//    implementation(project(":summer"))
}

val sourceJar by tasks.registering(Jar::class) {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.set("sources")
}

group = summerGroup
version = summerVersion

publishing {
    publications {
        create<MavenPublication>("summerAndroidSupport") {
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
        setPublications("summerAndroidSupport")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.6"
    }
}