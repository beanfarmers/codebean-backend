import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.asciidoctor.jvm.convert") version "3.3.2"

    val kotlinVersion = "1.6.10"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

group = "com.litsynp"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val asciidoctorExtensions: Configuration by configurations.creating
val snippetsDir by extra { file("build/generated-snippets") }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    implementation("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    asciidoctorExtensions("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.clean {
    delete("src/main/resources/static/docs")
}

tasks.test {
    systemProperty("org.springframework.restdocs.outputDir", snippetsDir)
    outputs.dir(snippetsDir)
}


tasks.build {
    dependsOn("copyDocument")
}

tasks.asciidoctor {
    dependsOn(tasks.test)

    attributes(
        mapOf("snippets" to snippetsDir)
    )
    inputs.dir(snippetsDir)

    doFirst {
        delete("src/main/resources/static/docs")
    }
}

tasks.register("copyDocument", Copy::class) {
    dependsOn(tasks.asciidoctor)
    from(tasks.asciidoctor.get().outputDir)
    into(file("src/main/resources/static/docs"))
}

tasks.bootJar {
    dependsOn(tasks.asciidoctor)

    from(tasks.asciidoctor.get().outputDir) {
        into("BOOT-INF/classes/static/docs")
    }
}
