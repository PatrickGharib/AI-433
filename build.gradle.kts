import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ai-project"
version = "1.0-SNAPSHOT"



plugins {
    kotlin("jvm") version "1.3.50"
    application
    java
}

repositories {
    mavenCentral()
}

application{
    mainClassName = "HelloWorldKt"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}