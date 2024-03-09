import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id ("org.liquibase.gradle") version "2.0.4"
	id("org.springframework.boot") version "2.7.17"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "ru.headh.kosti"
version = "1.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.auth0:java-jwt:4.4.0")

	implementation("org.springdoc:springdoc-openapi-kotlin:1.6.14")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.14")
	implementation("org.springdoc:springdoc-openapi-webmvc-core:1.6.14")

	// Migrations
	implementation("org.liquibase:liquibase-core:4.24.0")
	runtimeOnly("org.postgresql:postgresql")
	// Test
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	testImplementation("org.testcontainers:postgresql:1.16.3")
	testImplementation("org.testcontainers:junit-jupiter:1.16.3")
	testImplementation("org.mockito:mockito-core")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.mockito")
		exclude(group = "org.mockito:mockito-core")
	}
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
