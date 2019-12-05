import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "ai-project"
version = "1.0-SNAPSHOT"



plugins {
    kotlin("jvm") version "1.3.50"
    application
}

repositories {
    mavenCentral()
}

application{
    mainClassName = "HelloWorldKt"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}



tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
