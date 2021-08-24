package com.bbgo.module_sys.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_sys.bean.ArticleData
import com.bbgo.module_sys.bean.KnowledgeTree
import com.bbgo.module_sys.bean.NaviData
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class SysRepository private constructor(private val remoteRepository: SysRemoteRepository, private val localRepository: SysLocalRepository) {

    companion object {
        @Volatile
        private var repository: SysRepository? = null

        fun getInstance(remoteRepository: SysRemoteRepository, localRepository: SysLocalRepository): SysRepository {
            if (repository == null) {
                synchronized(SysRepository::class.java) {
                    if (repository == null) {
                        repository = SysRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }

    suspend fun getKnowledgeTree() : Flow<HttpResult<List<KnowledgeTree>>> {
        return remoteRepository.getKnowledgeTree()
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getKnowledgeList(id, page)
    }

    suspend fun getNavigationList() : Flow<HttpResult<List<NaviData>>> {
        return remoteRepository.getNavigationList()
    }
}