val jwtVersion: String = "0.11.5"
val tcVersion = "1.20.4"
val springDocVersion = "2.8.5"

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
	id("com.google.cloud.tools.jib") version "3.4.4"
}

group = "com.movieworld"
version = "0.0.1-SNAPSHOT"

extra["springCloudVersion"] = "2024.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations.compileOnly {
	extendsFrom(configurations.annotationProcessor.get())
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.2")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	//implementation("com.google.cloud.tools:jib-maven-plugin:$jibVersion")
	implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")
	implementation("org.postgresql:postgresql:42.2.24")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter:$tcVersion")
	testImplementation("org.testcontainers:testcontainers:$tcVersion")
	testImplementation("org.testcontainers:postgresql:$tcVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
	jvmArgs("-XX:+EnableDynamicAgentLoading")
}

jib {
	from {
		image = "amazoncorretto:21"
	}
	to {
		image = "thekotik/user-service"
		tags = setOf("latest", version.toString())
	}
	container {
		jvmFlags = listOf("-Xms512m", "-Xmx1024m")
		ports = listOf("8080")
		environment = mapOf(
			"SPRING_PROFILES_ACTIVE" to "prod"
		)
	}
}