import java.util.*

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
}

kotlin {
    jvm()
    iosArm64 {
        binaries {
            framework()
        }
        compilations.forEach { compilation ->
            compilation.kotlinOptions.freeCompilerArgs += "-Xobjc-generics"
        }
    }
    iosX64 {
        binaries {
            framework()
        }
        compilations.forEach { compilation ->
            compilation.kotlinOptions.freeCompilerArgs += "-Xobjc-generics"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
            }
        }
        getByName("jvmMain") {
            dependencies {
                implementation(kotlin("stdlib"))

            }
        }
        getByName("jvmTest") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
            }
        }
        getByName("iosArm64Main") {
            dependencies {

            }
        }

        getByName("iosX64Main").dependsOn(getByName("iosArm64Main"))

        all {
            languageSettings.enableLanguageFeature("InlineClasses")
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