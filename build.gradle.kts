plugins {
	java
	id("org.springframework.boot") version "4.1.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.vaadin.flow") version "25.3.6"
}

group = "com.mihirjha.cloudai"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

configurations {
    developmentOnly
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")

    implementation("com.fasterxml.jackson.core:jackson-databind")

    implementation(platform("software.amazon.awssdk:bom:2.53.1"))
    implementation("software.amazon.awssdk:s3")
    implementation("software.amazon.awssdk:rekognition")
    implementation("software.amazon.awssdk:bedrockruntime")


    implementation(enforcedPlatform("com.vaadin:vaadin-bom:25.3.4"))
    implementation("com.vaadin:vaadin-spring-boot-starter")

    developmentOnly("com.vaadin:vaadin-dev:25.3.4")

	compileOnly("org.projectlombok:lombok")

	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	
	testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
