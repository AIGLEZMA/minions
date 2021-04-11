import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "me.aiglez"
version = "2.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")  {
        exclude("org.yaml", "snakeyaml")
    }
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT") {
        exclude("org.yaml", "snakeyaml")
    }

    // configurate
    implementation("org.spongepowered:configurate-yaml:4.0.0")
    implementation("org.spongepowered:configurate-extra-kotlin:4.0.0")

    // test
    testImplementation("com.github.seeseemelk:MockBukkit:v1.8-spigot-SNAPSHOT")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())

        archiveFileName.set(rootProject.name + ".jar")
        exclude("META-INF/**")
        exclude("org.spigotmc:spigot")

        relocate("kotlin", "me.aiglez.minions.external.kotlin")
        relocate("net.kyori", "me.aiglez.minions.external.kyori")
        relocate("org.spongepowered", "me.aiglez.minions.external.spongepowered")
    }

    test {
        useJUnitPlatform()
    }
}