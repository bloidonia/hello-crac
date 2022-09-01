import java.net.URI

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.minimal.application") version "3.5.2-SNAPSHOT"
    id("io.micronaut.docker") version "3.5.2-SNAPSHOT"
    id("io.micronaut.crac") version "3.5.2-SNAPSHOT"
}

version = "0.1"
group = "com.bloidonia"

repositories {
    mavenLocal()
    maven { url = URI.create("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("jakarta.annotation:jakarta.annotation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut.crac:micronaut-crac:1.0.0-SNAPSHOT")
}

application {
    mainClass.set("com.bloidonia.Application")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.bloidonia.*")
    }
    crac {
        // The base warmup script just hits '/' lets use our own one that calls the correct endpoint
        warmupScript.set(project.layout.projectDirectory.dir("src/crac/scripts").file("warmup.sh"))
    }
}



