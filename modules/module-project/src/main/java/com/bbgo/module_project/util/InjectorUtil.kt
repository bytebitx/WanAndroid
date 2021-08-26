package com.bbgo.module_project.util

import com.bbgo.module_project.repository.ProjectLocalRepository
import com.bbgo.module_project.repository.ProjectRemoteRepository
import com.bbgo.module_project.repository.ProjectRepository
import com.bbgo.module_project.viewmodel.ProjectViewModelFactory

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getProjectViewModelFactory() = ProjectViewModelFactory(
        ProjectRepository.getInstance(ProjectRemoteRepository.instance, ProjectLocalRepository.getInstance()))
}