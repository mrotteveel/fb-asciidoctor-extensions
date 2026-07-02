// SPDX-FileCopyrightText: Copyright 2025-2026 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0

plugins {
    `java-library`
    `maven-publish`
    signing
}

description = "AsciidoctorJ docinfo processor to add a canonical link to HTML output"

extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")

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
            "License" to providers.gradleProperty("license.name"),
            "License-Url" to providers.gradleProperty("license.url")
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
                    name = providers.gradleProperty("license.name")
                    url = providers.gradleProperty("license.url")
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
                url = uri((if (extra["isReleaseVersion"] as Boolean) providers.gradleProperty("releaseRepository") else providers.gradleProperty("snapshotRepository")))
                credentials {
                    username = providers.gradleProperty("centralUsername").orNull
                    password = providers.gradleProperty("centralPassword").orNull
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    setRequired { (project.extra["isReleaseVersion"] as Boolean) && gradle.taskGraph.hasTask("publish") }
    sign(publishing.publications["maven"])
}
