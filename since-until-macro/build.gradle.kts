plugins {
    `java-library`
}

dependencies {
    compileOnly(libs.org.asciidoctor.asciidoctorj)

    testImplementation(platform(libs.junit.bom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(libs.org.asciidoctor.asciidoctorj)
}


