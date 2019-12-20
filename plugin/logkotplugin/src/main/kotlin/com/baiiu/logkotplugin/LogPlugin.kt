package com.baiiu.logkotplugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * author: zhuzhe
 * time: 2019-12-02
 * description:
 */
class LogPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("hello from kotlin log plugin: " + project.name)

        val android = project.extensions.getByType(AppExtension::class.java)

        android.registerTransform(LogTransform())
    }
}