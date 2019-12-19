import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    groovy
    `java-library`
    `maven-publish`
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            group = "com.baiiu.log"
            version = "1.0-SNAPSHOT"
            artifactId = "log"

            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("../repo/logrepo")
        }
    }
}



