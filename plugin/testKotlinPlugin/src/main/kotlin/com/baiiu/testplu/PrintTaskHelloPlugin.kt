package com.baiiu.testplu

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * author: zhuzhe
 * time: 2019-12-02
 * description:
 */
class PrintTaskHelloPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("this plugin is to show all tasks of this project: $this")

        project.afterEvaluate {
            println("###############################################################" + project.name)

            for ((key, value) in project.getAllTasks(true)) {
                println("key: $key, value: $value")

                for (task in value) {
                    println("task ${task.name} start------------------------------")

                    task.dependsOn.forEach { println("dependOn--> $it") }
                    task.inputs.files.files.forEach { println("input--> $it") }
                    task.outputs.files.files.forEach { println("output--> $it") }

                    println("------------------------------task ${task.name} end\n")
                }
            }


            println("###############################################################" + project.name)
        }
    }
}