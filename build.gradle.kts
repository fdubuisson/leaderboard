import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.5.2"
val ktorVersion = "1.6.3"
val koinVersion = "2.2.3"
val mongoVersion = "4.3.0"

plugins {
    application
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = "me.fdubuisson"
version = "1.0-SNAPSHOT"

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
    implementation("org.litote.kmongo:kmongo-coroutine:4.2.8")
    implementation("org.mongodb:mongodb-driver-async:3.12.10")

    implementation("ch.qos.logback:logback-classic:1.2.5")
    implementation("com.typesafe:config:1.4.1")

    testImplementation(kotlin("test"))
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinVersion")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("me.fdubuisson.leaderboard.MainKt")
}
