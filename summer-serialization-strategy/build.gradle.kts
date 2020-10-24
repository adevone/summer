plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("kotlinx-serialization")
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
        getByName("iosX64Main").dependsOn(getByName("iosArm64Main"))
    }
}

group = summerGroup
version = summerVersion