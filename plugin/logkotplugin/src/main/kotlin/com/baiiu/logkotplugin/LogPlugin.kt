package com.baiiu.logkotplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.api.transform.*

/**
 * author: zhuzhe
 * time: 2019-12-02
 * description:
 */
class LogPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("hello from kotlin log plugin: " + project.name)

    }
}