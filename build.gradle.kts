plugins {
    kotlin("jvm") version "2.0.20"
    id("com.gradleup.shadow") version "8.3.3"
    application
}

group = "com.llamalad7"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass = "com.llamalad7.ipcdemo.MainKt"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass
    }
}