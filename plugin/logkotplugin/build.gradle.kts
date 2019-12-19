import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    groovy
    `java-library`
    `maven-publish`
}


dependencies {
    implementation("org.ow2.asm:asm:7.2")
    implementation("com.android.tools.build:gradle:3.4.2")
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



