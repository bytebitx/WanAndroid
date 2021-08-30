package com.bbgo.module_project.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_project.bean.ArticleData
import com.bbgo.module_project.bean.ProjectBean
import com.bbgo.module_project.net.HttpProjectService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class ProjectRemoteRepository @Inject constructor(){

    suspend fun getProjectTree() : Flow<HttpResult<List<ProjectBean>>> {
        return flow {
            emit(HttpProjectService.service.getProjectTree())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProjectList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return flow {
            emit(HttpProjectService.service.getProjectList(page, id))
        }.flowOn(Dispatchers.IO)
    }
}