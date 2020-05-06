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
        compilations.forEach { compilation ->
            compilation.kotlinOptions.freeCompilerArgs += "-Xobjc-generics"
        }
    }
    iosArm32 {
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
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")
                implementation("com.github.adevone.summer:summer:$summerVersion")
//                implementation(project(":summer"))
            }
        }
        getByName("jvmMain") {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
            }
        }
        getByName("iosArm64Main") {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationVersion")
            }
        }
        getByName("iosX64Main").dependsOn(getByName("iosArm64Main"))
    }
}