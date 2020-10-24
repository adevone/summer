import java.util.*

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("kotlinx-serialization")
    id("maven-publish")
}

kotlin {
    jvm()
    iosArm64 {
        binaries {
            framework()
        }
    }
    iosArm32 {
        binaries {
            framework()
        }
    }
    iosX64 {
        binaries {
            framework()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation("com.github.adevone.summer:summer:$summerVersion")
//                implementation(project(":summer"))
            }
        }
        getByName("jvmMain") {
            dependencies {
            }
        }
        getByName("iosArm64Main") {
            dependencies {
            }
        }
        getByName("iosArm32Main").dependsOn(getByName("iosArm64Main"))
        getByName("iosX64Main").dependsOn(getByName("iosArm64Main"))
    }
}

group = summerGroup
version = summerVersion

val propsFile = File(rootProject.rootDir, "bintray.properties")
if (propsFile.exists()) {
    publishing {
        val bintrayProps = Properties().apply {
            load(propsFile.inputStream())
        }
        repositories {
            maven("https://api.bintray.com/maven/summermpp/summer/summer/;publish=0") {
                name = "bintray"

                credentials {
                    username = bintrayProps.getProperty("USERNAME")
                    password = bintrayProps.getProperty("API_KEY")
                }
            }
        }
    }
}