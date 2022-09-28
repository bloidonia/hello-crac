import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import java.net.URI

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.minimal.application") version "3.6.0"
    id("io.micronaut.docker") version "3.6.0"
    id("io.micronaut.crac") version "3.6.0"
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
    implementation("io.micronaut.crac:micronaut-crac")
}

application {
    mainClass.set("com.bloidonia.Application")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.named("dockerBuildCrac", DockerBuildImage::class) {
    images.set(listOf("timyates/hello:latest"))
}

tasks.named("dockerPushCrac", DockerPushImage::class) {
    images.set(listOf("timyates/hello:latest"))
    registryCredentials {
        username.set(System.getenv("DOCKER_USERNAME"))
        password.set(System.getenv("DOCKER_PASSWORD"))
        email.set(System.getenv("DOCKER_EMAIL"))
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



