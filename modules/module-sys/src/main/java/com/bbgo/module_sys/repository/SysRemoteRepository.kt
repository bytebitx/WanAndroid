package com.bbgo.module_sys.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_sys.bean.ArticleData
import com.bbgo.module_sys.bean.KnowledgeTree
import com.bbgo.module_sys.bean.NaviData
import com.bbgo.module_sys.net.HttpSysService
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
class SysRemoteRepository @Inject constructor(){

    fun getKnowledgeTree() : Flow<HttpResult<List<KnowledgeTree>>> {
        return HttpSysService.service.getKnowledgeTree()
    }

    fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return HttpSysService.service.getKnowledgeList(id, page)
    }

    fun getNavigationList() : Flow<HttpResult<List<NaviData>>> {
        return HttpSysService.service.getNavigationList()
    }

}