plugins {
    kotlin("jvm") version "1.6.21"
}

group = "com.iamslash"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // logging
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.2.51")
}
