plugins {
    kotlin("jvm") version "1.4.10"
}

group = "ru.haliksar.coroutine_samples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.0")
}
