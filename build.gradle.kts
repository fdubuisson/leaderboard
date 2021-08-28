import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.fdubuisson"
version = "1.0-SNAPSHOT"

val ktorVersion = "1.6.3"
val koinVersion ="2.2.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.5")

    implementation("io.insert-koin:koin-core:$koinVersion")

    testImplementation(kotlin("test"))
    testImplementation("io.insert-koin:koin-test:$koinVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}