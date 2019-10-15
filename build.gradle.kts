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
        maven {
            url = uri("https://jitpack.io")
//            content {
//                // Exclude Kodein artifacts
//                excludeGroupByRegex("org\\.kodein.*")
//            }
        }
//        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-DI")
        maven {
            url = uri(project.extra["REPO_URL"].toString() + "/" + project.extra["REPO_KEY"].toString())
            credentials {
                username = project.extra["REPO_USERNAME"].toString()
                password = project.extra["REPO_ENCRYPTED_PASSWORD"].toString()
            }
        }
    }
}

tasks.create<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
}