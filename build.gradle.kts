plugins {
    java
}

repositories {
    mavenCentral()
}

group = "com.dvoraksw.cch"
version = "0.1.0"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    implementation("com.google.code.gson:gson:2.10.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes["Built-By"] = System.getProperty("user.name")
        attributes["Specification-Title"] = project.name
        attributes["Specification-Version"] = "${project.version}"
        attributes["Specification-Vendor"] = "Jan Dvorak"
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = "${project.version}"
        attributes["Implementation-Vendor"] = "Jan Dvorak"
        attributes["Created-By"] = "Gradle ${project.gradle.gradleVersion}"
        attributes["Build-Jdk"] = "${System.getProperty("java.version")} (${System.getProperty("java.vendor").replace("N/A", "Arch Linux")} ${System.getProperty("java.vm.version")})"
        attributes["Build-OS"] = "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${System.getProperty("os.version")}"
    }
    exclude("**/*.xcf")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-Xshare:off")
    testLogging {
        events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR")
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}
