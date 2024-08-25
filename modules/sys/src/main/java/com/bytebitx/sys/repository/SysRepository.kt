package com.bytebitx.sys.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.sys.bean.ArticleData
import com.bytebitx.sys.bean.KnowledgeTree
import com.bytebitx.sys.bean.NaviData
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