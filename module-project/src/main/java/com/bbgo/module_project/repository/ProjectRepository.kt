package com.bbgo.module_project.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_project.bean.ArticleData
import com.bbgo.module_project.bean.ProjectBean
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class ProjectRepository private constructor(private val remoteRepository: ProjectRemoteRepository, private val localRepository: ProjectLocalRepository) {

    companion object {
        @Volatile
        private var repository: ProjectRepository? = null

        fun getInstance(remoteRepository: ProjectRemoteRepository, localRepository: ProjectLocalRepository): ProjectRepository {
            if (repository == null) {
                synchronized(ProjectRepository::class.java) {
                    if (repository == null) {
                        repository = ProjectRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }

    suspend fun getProjectTree() : Flow<HttpResult<List<ProjectBean>>> {
        return remoteRepository.getProjectTree()
    }

    suspend fun getProjectList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getProjectList(id, page)
    }

}