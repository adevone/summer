import java.util.*

plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

kotlin {
    jvm()
    js(IR) {
        browser()
    }
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

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            }
        }
        val jsMain by getting {
            dependencies {

            }
        }
        val iosArm64Main by getting {
            dependencies {

            }
        }
        val iosX64Main by getting {
            dependsOn(iosArm64Main)
        }
        val iosArm32Main by getting {
            dependsOn(iosArm64Main)
        }
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
            maven("https://api.bintray.com/maven/summermpp/summer/summer/;publish=0;override=1") {
                name = "bintray"

                credentials {
                    username = bintrayProps.getProperty("USERNAME")
                    password = bintrayProps.getProperty("API_KEY")
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