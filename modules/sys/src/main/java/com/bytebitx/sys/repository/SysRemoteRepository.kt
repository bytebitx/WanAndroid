package com.bytebitx.sys.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.base.net.ServiceCreators
import com.bytebitx.sys.bean.ArticleData
import com.bytebitx.sys.bean.KnowledgeTree
import com.bytebitx.sys.bean.NaviData
import com.bytebitx.sys.net.api.HttpSysApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class SysRemoteRepository @Inject constructor(){

    private val service = ServiceCreators.create(HttpSysApiService::class.java)

    fun getKnowledgeTree() : Flow<HttpResult<List<KnowledgeTree>>> {
        return service.getKnowledgeTree()
    }

    fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return service.getKnowledgeList(id, page)
    }

    fun getNavigationList() : Flow<HttpResult<List<NaviData>>> {
        return service.getNavigationList()
    }

}