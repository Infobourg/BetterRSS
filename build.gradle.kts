
import com.diffplug.gradle.spotless.SpotlessExtension
import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.google.cloud.tools:appengine-gradle-plugin:2.3.0")
    }
}

plugins {
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("com.diffplug.spotless") version "5.1.0"
}

apply(plugin = "com.google.cloud.tools.appengine")

group = "org.infobourg"
version = "0.0.1-SNAPSHOT"

configurations {
    compileOnly {
        extendsFrom(configurations["annotationProcessor"])
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(kotlin("reflect"))

    implementation("com.slack.api:bolt:1.0.8")
    implementation("com.slack.api:bolt-servlet:1.0.8")

    implementation("com.slack.api:slack-api-client:1.0.11")

    implementation("com.rometools:rome:1.14.1")

    implementation("io.ktor:ktor-client-core:1.3.1")
    implementation("io.ktor:ktor-client-apache:1.3.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("junit:junit:4.13")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
    }
}

configure<SpotlessExtension> {
    kotlin {
        ktlint("0.37.2")
    }
    kotlinGradle {
        ktlint()
    }
}

configure<AppEngineAppYamlExtension> {
    deploy {
        stopPreviousVersion = true
        promote = true
        projectId = "GCLOUD_CONFIG"
        version = "GCLOUD_CONFIG"
    }
}
