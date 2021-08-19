package com.bbgo.module_project.repository

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class ProjectLocalRepository private constructor(){



    companion object {
        @Volatile
        private var repository: ProjectLocalRepository? = null

        fun getInstance(): ProjectLocalRepository {
            if (repository == null) {
                synchronized(ProjectRepository::class.java) {
                    if (repository == null) {
                        repository = ProjectLocalRepository()
                    }
                }
            }
            return repository!!
        }
    }
}