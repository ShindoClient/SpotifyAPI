plugins {
    alias(libs.plugins.kotlin.jvm)
}

base {
    group = "com.shindoclient"
    archivesName = "ShindoSpotify"
    version = "1.0.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        jvmToolchain(8)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.spotify.web.api)
    compileOnly(libs.okhttp)
}
