package com.bytebitx.project.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.base.net.ServiceCreators
import com.bytebitx.project.bean.ArticleData
import com.bytebitx.project.bean.ProjectBean
import com.bytebitx.project.net.api.HttpProjectApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class ProjectRemoteRepository @Inject constructor(){

    private val service = ServiceCreators.create(HttpProjectApiService::class.java)

    fun getProjectTree() : Flow<HttpResult<List<ProjectBean>>> {
        return service.getProjectTree()
    }

    fun getProjectList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return service.getProjectList(page, id)
    }
}