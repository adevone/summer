import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("co.touchlab.kotlinxcodesync")
}

android {
    compileSdkVersion(targetVersion)

    defaultConfig {
        minSdkVersion(minVersion)
        targetSdkVersion(targetVersion)
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

xcode {
    projectPath = "../iosApp/iosApp.xcodeproj"
    target = "iosApp"
}

kotlin {
    iosArm64("ios") {
        binaries {
            framework {
                freeCompilerArgs.add("-Xobjc-generics")
                embedBitcode("disable")
            }
        }
    }
    iosX64("iossim") {
        binaries {
            framework {
                freeCompilerArgs.add("-Xobjc-generics")
                embedBitcode("disable")
            }
        }
    }
    android()

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion")

                implementation("org.kodein.di:kodein-di-erased:$kodeinVersion")
                implementation("com.russhwolf:multiplatform-settings:$multiplatformSettingVersion")

                implementation("com.github.adevone.summer:summer:$summerVersion")
//                implementation(project(":summer"))
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
            }
        }
        getByName("iosMain") {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutinesVersion")
            }
        }
        getByName("iosSimMain").dependsOn(getByName("iosMain"))
    }
}

tasks.create("copyFramework") {
    val buildType = project.findProperty("kotlin.build.type")?.toString() ?: "DEBUG"
    dependsOn("link${buildType.toLowerCase().capitalize()}FrameworkIosSim")

    doLast {
        val target = kotlin.targets.getByName("iosSim") as KotlinNativeTarget
        val srcFile = target.binaries.getFramework(buildType).outputFile
        val targetDir = project.property("configuration.build.dir")!!
        copy {
            from(srcFile.parent)
            into(targetDir)
            include("shared.framework/**")
            include("shared.framework.dSYM")
        }
    }
}