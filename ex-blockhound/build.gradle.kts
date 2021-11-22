plugins {
    java
    kotlin("jvm") version "1.5.21"
}

group = "com.iamslash"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug")
    implementation("io.projectreactor.tools:blockhound:1.0.4.RELEASE")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testImplementation("io.mockk:mockk:1.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
