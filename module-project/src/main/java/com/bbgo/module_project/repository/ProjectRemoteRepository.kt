package com.bbgo.module_project.repository

import com.bbgo.module_project.bean.Articles
import com.bbgo.module_project.bean.ProjectTree
import com.bbgo.module_project.net.HttpProjectService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
class ProjectRemoteRepository private constructor(){

    suspend fun getProjectTree() : Flow<ProjectTree> {
        return flow {
            emit(HttpProjectService.service.getProjectTree())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProjectList(id: Int, page: Int) : Flow<Articles> {
        return flow {
            emit(HttpProjectService.service.getProjectList(page, id))
        }.flowOn(Dispatchers.IO)
    }


    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = ProjectRemoteRepository()
    }
}