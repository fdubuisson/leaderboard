import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = "me.fdubuisson"
version = "1.0-SNAPSHOT"

val ktorVersion = "1.6.3"
val koinVersion = "2.2.3"
val mongoVersion = "4.3.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinVersion")

    implementation("org.litote.kmongo:kmongo:4.2.8")
    implementation("org.mongodb:mongodb-driver-sync:$mongoVersion")

    implementation("ch.qos.logback:logback-classic:1.2.5")
    implementation("com.typesafe:config:1.4.1")

    testImplementation(kotlin("test"))
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
