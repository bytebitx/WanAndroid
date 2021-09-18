package com.bbgo.login_plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


class LoginPlugin implements Plugin<Project> {
    void apply(Project project) {
        println("------LifeCycle plugin entrance-------")
        //注册transform
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new LoginTransform())
    }
}