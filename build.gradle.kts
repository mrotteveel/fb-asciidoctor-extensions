// SPDX-FileCopyrightText: Copyright 2025 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0

allprojects {
    repositories {
        mavenCentral()
    }

    group = "org.firebirdsql.asciidoctor"
    version = "1.0-SNAPSHOT"

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc>().configureEach {
        options.encoding = "UTF-8"
        (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:-missing", true)
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    tasks.withType<PublishToMavenRepository>().onEach {
        it.doFirst {
            if (findProperty("centralUsername") == null || findProperty("centralPassword") == null) {
                throw RuntimeException("No credentials for publishing, make sure to specify the properties " +
                        "credentialsPassphrase, or centralUsername and centralPassword. See devdoc/deploy.md for details.")
            }
        }
    }

}
