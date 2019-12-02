package com.baiiu.testplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task("helloPlugin") {
            doLast {
                println("Hello from the alone GreetingPlugin")
            }
        }
    }
}