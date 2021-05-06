import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`
import org.gradle.kotlin.dsl.signing
import java.util.*

plugins {
    `maven-publish`
    signing
}

val javadocsJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

val propsFile = File(rootProject.rootDir, "sonatype.properties")
if (propsFile.exists()) {
    val sonatypeProps = Properties().apply {
        load(propsFile.inputStream())
    }
    publishing {
        repositories {
            maven {
                name = "sonatype"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = sonatypeProps.getProperty("SONATYPE_USER")
                    password = sonatypeProps.getProperty("SONATYPE_PASSWORD")
                }
            }
        }
        publications.withType<MavenPublication> {
            artifact(javadocsJar)
            pom {
                description.set("A Kotlin/Multiplatform MVVM library")
                name.set(project.name)
                url.set("https://github.com/adevone/summer")
                scm {
                    connection.set("scm:git:github.com/adevone/summer.git")
                    developerConnection.set("scm:git:ssh://github.com/adevone/summer.git")
                    url.set("https://github.com/adevone/summer/tree/master")
                }
                developers {
                    developer {
                        id.set("adevone")
                        name.set("Kirill Terekhov")
                        email.set("terehovks3@gmail.com")
                    }
                }
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/adevone/summer/blob/master/LICENSE.txt")
                    }
                }
            }
        }
    }
}

signing {
    sign(publishing.publications)
}