plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("kotlinx-serialization")
    id("convention.publication.multiplatform")
}

kotlin {
    jvm()
    ios {
        binaries {
            framework()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
                implementation(project(":summer"))
            }
        }
        getByName("jvmMain") {
            dependencies {
            }
        }
        getByName("iosMain") {
            dependencies {
            }
        }
    }
}

group = summerGroup
version = summerVersion

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.6"
    }
}