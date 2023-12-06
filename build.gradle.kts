import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
	id ("org.liquibase.gradle") version "2.0.4"
	id("org.springframework.boot") version "2.7.17"
	id("org.openapi.generator") version "6.6.0"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	kotlin("plugin.jpa") version "1.6.21"
}

group = "ru.headh.kosti"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core:4.24.0")
	implementation("com.auth0:java-jwt:4.4.0")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
}

tasks.register("generateUserClient", GenerateTask::class) {
	group = "openapi tools"
	input = project.file("$projectDir/src/main/resources/openapi/user-service/api-docs.yaml").path
	outputDir.set("$buildDir/generated")
	modelPackage.set("ru.headh.kosti.apigateway.client.model")
	apiPackage.set("ru.headh.kosti.apigateway.client.api")
	generatorName.set("kotlin")
	modelNameSuffix.set("Gen")
	templateDir.set("$projectDir/src/main/resources/openapi/templates")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8-localdatetime",
			"useTags" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"serializationLibrary" to "jackson",
			"useCoroutines" to "true"
		)
	)
	additionalProperties.set(
		mapOf(
			"removeEnumValuePrefix" to "false"
		)
	)
}

tasks.register("generateHomeClient", GenerateTask::class) {
	group = "openapi tools"
	input = project.file("$projectDir/src/main/resources/openapi/home-service/api-docs.yaml").path
	outputDir.set("$buildDir/generated")
	modelPackage.set("ru.headh.kosti.apigateway.client.model")
	apiPackage.set("ru.headh.kosti.apigateway.client.api")
	generatorName.set("kotlin")
	modelNameSuffix.set("Gen")
	templateDir.set("$projectDir/src/main/resources/openapi/templates")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8-localdatetime",
			"useTags" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"serializationLibrary" to "jackson",
			"useCoroutines" to "true"
		)
	)
	additionalProperties.set(
		mapOf(
			"removeEnumValuePrefix" to "false"
		)
	)
}

tasks.register("generateDeviceClient", GenerateTask::class) {
	group = "openapi tools"
	input = project.file("$projectDir/src/main/resources/openapi/device-service/api-docs.yaml").path
	outputDir.set("$buildDir/generated")
	modelPackage.set("ru.headh.kosti.apigateway.client.model")
	apiPackage.set("ru.headh.kosti.apigateway.client.api")
	generatorName.set("kotlin")
	modelNameSuffix.set("Gen")
	templateDir.set("$projectDir/src/main/resources/openapi/templates")
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8-localdatetime",
			"useTags" to "true",
			"enumPropertyNaming" to "UPPERCASE",
			"serializationLibrary" to "jackson",
			"useCoroutines" to "true"
		)
	)
	additionalProperties.set(
		mapOf(
			"removeEnumValuePrefix" to "false"
		)
	)
}

tasks.withType<KotlinCompile> {
	dependsOn("generateUserClient")
	dependsOn("generateHomeClient")
	dependsOn("generateDeviceClient")
	kotlinOptions {//
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
	main {
		java {
			srcDir("${buildDir.absoluteFile}/generated/src/main/kotlin")
		}
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