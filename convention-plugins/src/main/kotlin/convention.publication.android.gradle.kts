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

afterEvaluate {
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
            publications {
                create<MavenPublication>("summerAndroid") {
                    groupId = project.group.toString()
                    artifactId = project.name
                    version = project.version.toString()

                    artifact(javadocsJar)
                    artifact("$buildDir/outputs/aar/${project.name}-release.aar")

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
                        // A slightly hacky fix so that your POM will include any transitive dependencies
                        // that your library builds upon
                        withXml {
                            val dependenciesNode = asNode().appendNode("dependencies")
                            project.configurations.getByName("implementation").allDependencies.forEach {
                                val dependencyNode = dependenciesNode.appendNode("dependency")
                                dependencyNode.appendNode("groupId", it.group)
                                dependencyNode.appendNode("artifactId", it.name)
                                dependencyNode.appendNode("version", it.version)
                            }
                        }
                    }
                }
            }
        }
    }
    signing {
        sign(publishing.publications)
    }
}