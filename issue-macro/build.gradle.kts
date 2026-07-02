// SPDX-FileCopyrightText: Copyright 2026 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0

plugins {
    `java-library`
    `maven-publish`
    signing
}

description = "AsciidoctorJ issue link macro"

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
            "Automatic-Module-Name" to "org.firebirdsql.asciidoctor.issue",
            "License" to providers.gradleProperty("license.name"),
            "License-Url" to providers.gradleProperty("license.url"),
            "SPDX-FileCopyrightText" to "Copyright 2026 Mark Rotteveel",
            "SPDX-License-Identifier" to "LicenseRef-IDPL-1.0"
        )
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
        pom {
            name = "AsciidoctorJ Extensions for Firebird Documentation: issue-macro"
            description = "AsciidoctorJ issue link macro"
            url = "https://github.com/mrotteveel/fb-asciidoctor-extensions"
            inceptionYear = "2026"

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
                url = uri(providers.gradleProperty(if (extra["isReleaseVersion"] as Boolean) "releaseRepository" else "snapshotRepository"))
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
