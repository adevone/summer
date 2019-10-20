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
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                
            }
        }
        getByName("jvmMain"){
            dependencies {
                implementation(kotlin("stdlib"))
            }
        }
        getByName("jsMain"){
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        getByName("iosMain"){
            dependencies {
            }
        }

        getByName("iosSimMain").dependsOn(getByName("iosMain"))
    }
}

group = "com.github.adevone"
version = summerVersion