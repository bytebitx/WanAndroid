package com.bbgo.module_sys.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_sys.bean.ArticleData
import com.bbgo.module_sys.bean.KnowledgeTree
import com.bbgo.module_sys.bean.NaviData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
@ActivityRetainedScoped
class SysRepository @Inject constructor(private val remoteRepository: SysRemoteRepository, private val localRepository: SysLocalRepository) {

    fun getKnowledgeTree() : Flow<HttpResult<List<KnowledgeTree>>> {
        return remoteRepository.getKnowledgeTree()
    }

    fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getKnowledgeList(id, page)
    }

    fun getNavigationList() : Flow<HttpResult<List<NaviData>>> {
        return remoteRepository.getNavigationList()
    }
}