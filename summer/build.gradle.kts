plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("maven-publish")
}

kotlin {
    jvm()
    iosArm64("ios")  {
        binaries {
            framework {
                freeCompilerArgs.add("-Xobjc-generics")
            }
        }
    }
    iosX64("iosSim")  {
        binaries {
            framework {
                freeCompilerArgs.add("-Xobjc-generics")
            }
        }
    }
    js()

    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        getByName("jvmMain"){
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
                implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
            }
        }
        getByName("jsMain"){
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("io.ktor:ktor-client-core-js:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")
            }
        }
        getByName("iosMain"){
            dependencies {
                implementation("io.ktor:ktor-client-core-native:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationVersion")
            }
        }

        getByName("iosSimMain").dependsOn(getByName("iosMain"))
    }
}

group = "summer"
version = summerVersion

publishing {
    repositories {
        maven {
            url = uri(project.extra["REPO_URL"].toString() + "/" + project.extra["REPO_KEY"].toString())
            credentials {
                username = project.extra["REPO_USERNAME"].toString()
                password = project.extra["REPO_ENCRYPTED_PASSWORD"].toString()
            }
        }
    }
}