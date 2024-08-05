import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.spring") version "1.9.0"
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

group = "com.atipera"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(project(":monolith:app:application"))
    implementation(project(":monolith:app:infrastructure"))
    implementation(project(":monolith:app:interfaces"))
    implementation(project(":monolith:app:ports-input"))
    implementation(project(":monolith:app:ports-output"))




    implementation("com.playtika.reactivefeign:feign-reactor:4.0.3")
    implementation("com.playtika.reactivefeign:feign-reactor-core:4.0.3")
    implementation("com.playtika.reactivefeign:feign-reactor-spring-configuration:4.0.3")
    implementation("com.playtika.reactivefeign:feign-reactor-webclient:4.0.3")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor:reactor-core")
    implementation("io.projectreactor.netty:reactor-netty")



    implementation (group= "org.springdoc", name= "springdoc-openapi-starter-webmvc-ui", version= "2.0.2")

    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:4.1.4")
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier:4.1.4")
    testImplementation("com.github.tomakehurst:wiremock:3.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.getByName<Jar>("jar") {
    archiveFileName.set("config.jar")
}
