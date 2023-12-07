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

extra["springCloudVersion"] = "2023.0.0"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.telegram:telegrambots-spring-boot-starter:6.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.register("generateGatewayClient", GenerateTask::class) {
    group = "openapi tools"
    input = project.file("$projectDir/src/main/resources/openapi/api-gateway/api-docs.yaml").path
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
    dependsOn("generateGatewayClient")
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
