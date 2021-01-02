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
                api("com.github.adevone.summer:summer:$summerVersion")
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
            maven("https://api.bintray.com/maven/summermpp/summer/${project.name}/;publish=0;override=1") {
                this.name = "bintray"

                credentials {
                    this.username = bintrayProps.getProperty("USERNAME")
                    this.password = bintrayProps.getProperty("API_KEY")
                }
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.6"
    }
}