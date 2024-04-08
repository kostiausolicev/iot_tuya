import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    id("org.springframework.boot") version "2.7.17"
    id("org.openapi.generator") version "6.6.0"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
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
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.telegram:telegrambots-spring-boot-starter:6.8.0")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("org.springframework.boot:spring-boot-starter-aop:2.7.17")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.data:spring-data-redis:2.7.17")
    implementation ("redis.clients:jedis:3.10.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("ch.qos.logback:logback-classic:1.2.9")
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
