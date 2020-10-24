import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    }
}

subprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://kotlin.bintray.com/ktor")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        maven(url = "https://dl.bintray.com/russhwolf/multiplatform-settings")
        maven(url = "https://dl.bintray.com/summermpp/summer")
        maven {
            url = uri("https://jitpack.io")
//            content {
//                // Exclude Kodein artifacts
//                excludeGroupByRegex("org\\.kodein.*")
//            }
        }

//        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-DI")
    }
}

tasks.create<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}