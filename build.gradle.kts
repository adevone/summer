buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-dev")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha03")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
    }
}

subprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://kotlin.bintray.com/ktor")
        maven(url = "https://dl.bintray.com/kotlin/kotlin-dev")
        maven(url = "https://dl.bintray.com/summermpp/summer")
    }
}