import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    groovy
    `java-library`
    `maven-publish`
}

group = "com.baiiu.kotdemo"
version = "1.0-SNAPSHOT"

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "hello"

            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("../kotrepo")
        }
    }
}

