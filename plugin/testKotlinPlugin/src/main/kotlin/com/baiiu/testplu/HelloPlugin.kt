package com.baiiu.testplu

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * author: zhuzhe
 * time: 2019-12-02
 * description:
 */
class HelloPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("hello from kotlin standalone plugin")

        target.task("helloKotPlugin") {
            doLast {
                println("Hello from the kotlin alone GreetingPlugin")
            }
        }
    }
}