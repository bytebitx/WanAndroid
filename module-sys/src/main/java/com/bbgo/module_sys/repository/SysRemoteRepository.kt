package com.bbgo.module_sys.repository

import com.bbgo.module_sys.bean.*
import com.bbgo.module_sys.net.HttpSysService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
class SysRemoteRepository private constructor(){

    suspend fun getKnowledgeTree() : Flow<HttpResult<List<KnowledgeTree>>> {
        return flow {
            emit(HttpSysService.service.getKnowledgeTree())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return flow {
            emit(HttpSysService.service.getKnowledgeList(id, page))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getNavigationList() : Flow<HttpResult<List<NaviData>>> {
        return flow {
            emit(HttpSysService.service.getNavigationList())
        }.flowOn(Dispatchers.IO)
    }


    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = SysRemoteRepository()
    }
}