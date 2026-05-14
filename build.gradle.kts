import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    kotlin("plugin.jpa") version "2.3.21"
    kotlin("kapt") version "2.3.21"

    id("org.springframework.boot") version "4.0.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.13.1"

    id("org.openapi.generator") version "7.17.0"
}

group = "mu.architecture"
version = "0.0.1-SNAPSHOT"
description = "modulith"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

val openApiFiles = "$projectDir/openapi/"

extra["springModulithVersion"] = "2.0.6"

dependencies {
    val mapstruct = "1.6.3"
    val swaggerAnnotations = "2.2.49"
    val jakartaValidation = "4.0.0-M1"

    //Data + Web + rabbitmq
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    //Database + Liquibase
    implementation("org.springframework.boot:spring-boot-starter-liquibase")
    implementation("org.springframework.boot:spring-boot-h2console")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    //Modulith
    implementation("org.springframework.modulith:spring-modulith-starter-jpa")
    implementation("org.springframework.modulith:spring-modulith-events-api")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    runtimeOnly("org.springframework.modulith:spring-modulith-events-amqp")
    //Tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    //MapStruct
    implementation("org.mapstruct:mapstruct:$mapstruct")
    kapt("org.mapstruct:mapstruct-processor:$mapstruct")
    //OpenAPI
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerAnnotations")
    implementation("jakarta.validation:jakarta.validation-api:$jakartaValidation")
    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-amqp-test")
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
    includeCompileClasspath = false
}

sourceSets {
    main {
        kotlin.srcDir(
            tasks.withType<GenerateTask>()
                .map { it.outputDir.get() }
                .map { it -> "$it/src/main/kotlin" }
        )
        java.srcDir("src/main/kotlin")
    }
}


allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

fileTree(openApiFiles) { include("**/*.yaml", "**/*.yml") }
    .forEach { openApiFile ->
        val apiName = openApiFile.nameWithoutExtension.lowercase()

        tasks.register<GenerateTask>("generateKotlinClient_$apiName") {
            group = "openapi-generation"
            description = "Generates Kotlin client for $apiName"

            generatorName.set("kotlin")
            library.set("")
            inputSpec.set(openApiFile.path)
            outputDir.set("$projectDir/build/generated/openapi/$apiName")
            apiPackage.set("mu.architecture.modulith.${apiName}.api")
            modelPackage.set("mu.architecture.modulith.${apiName}.model")
            skipValidateSpec.set(true)
            validateSpec.set(true)
            configOptions.set(
                mapOf(
                    "serializationLibrary" to "jackson",
                    "collectionType" to "java.util.AbstractList",
                    "dateLibrary" to "java17",
                    "enumPropertyNaming" to "UPPERCASE",
                    "java25" to "true",
                    "interfaceOnly" to "true",
                    "library" to "jvm-spring-restclient",
                    "openApiNullable" to "false",
                    "useSpringBoot3" to "true",
                    "useJackson3" to "true",
                    "useJakartaEe" to "true",
                )
            )
            generateApiTests.set(false)
            generateApiDocumentation.set(false)
            generateModelTests.set(false)
            generateModelDocumentation.set(false)
        }
    }

val openApiGeneratorTasks = tasks.matching { it.name.startsWith("generateKotlinClient_") }

tasks.named("compileKotlin") {
    dependsOn(openApiGeneratorTasks)
}

tasks.matching { it.name == "kaptGenerateStubsKotlin" || it.name == "compileKotlin" }.configureEach {
    dependsOn(openApiGeneratorTasks)
}