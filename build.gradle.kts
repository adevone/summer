import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:$atomicfuVersion")
        classpath("co.touchlab:kotlinxcodesync:0.1.5")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.+")
    }
}

subprojects {

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    repositories {
        google()
        jcenter()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://kotlin.bintray.com/ktor")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        maven(url = "https://dl.bintray.com/russhwolf/multiplatform-settings")
        maven(url = "https://adevone.bintray.com/summer")
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