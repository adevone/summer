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
    iosX64("iossim")  {
        binaries {
            framework {
                freeCompilerArgs.add("-Xobjc-generics")
            }
        }
    }
    js()

    sourceSets {
        commonMain {
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
        getByName("jvmTest") {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
                implementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
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

        getByName("iossimMain").dependsOn(getByName("iosMain"))
    }
}

group = "com.github.adevone.summer"
version = summerVersion