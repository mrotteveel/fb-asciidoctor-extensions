// SPDX-FileCopyrightText: Copyright 2025 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0
import nu.studer.gradle.credentials.domain.CredentialsContainer

plugins {
    `java-library`
    `maven-publish`
    signing
    id("nu.studer.credentials").version("3.0")
}

description = "AsciidoctorJ docinfo processor to add a canonical link to HTML output"

extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")
val credentials = properties["credentials"] as CredentialsContainer
extra["signing.password"] = credentials.forKey("signing.password")
extra["centralPassword"] = credentials.forKey("centralPassword")

dependencies {
    compileOnly(libs.org.asciidoctor.asciidoctorj)

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.org.asciidoctor.asciidoctorj)
    testImplementation(libs.hamcrest)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.jar {
    manifest {
        attributes(
            "Automatic-Module-Name" to "org.firebirdsql.asciidoctor.canonical",
            "License" to properties["license.name"],
            "License-Url" to properties["license.url"]
        )
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        pom {
            name = "AsciidoctorJ Extensions for Firebird Documentation: canonical-link"
            description = "AsciidoctorJ docinfo processor to add a canonical link to HTML output"
            url = "https://github.com/mrotteveel/fb-asciidoctor-extensions"
            inceptionYear = "2025"

            developers {
                developer {
                    id = "mrotteveel"
                    name = "Mark Rotteveel"
                    email = "mark@lawinegevaar.nl"
                    roles = setOf("Administrator")
                }
            }
            licenses {
                license {
                    name = project.properties["license.name"] as String
                    url = project.properties["license.url"] as String
                    distribution = "repo"
                }
            }
            scm {
                connection = "scm:git:https://github.com/mrotteveel/fb-asciidoctor-extensions.git"
                developerConnection = "scm:git:git@github.com:mrotteveel/fb-asciidoctor-extensions.git"
                url = "https://github.com/mrotteveel/fb-asciidoctor-extensions"
            }
            issueManagement {
                system = "GitHub"
                url = "https://github.com/mrotteveel/fb-asciidoctor-extensions/issues"
            }
        }
        repositories {
            maven {
                url = uri((if (extra["isReleaseVersion"] as Boolean) properties["releaseRepository"] else properties["snapshotRepository"]) as String)
                credentials {
                    username = findProperty("centralUsername") as String?
                    password = findProperty("centralPassword") as String?
                }
            }
        }
    }
}

signing {
    setRequired { (project.extra["isReleaseVersion"] as Boolean) && gradle.taskGraph.hasTask("publish") }
    sign(publishing.publications["maven"])
}
