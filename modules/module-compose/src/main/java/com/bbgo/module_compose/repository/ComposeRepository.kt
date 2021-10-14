package com.bbgo.module_compose.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_compose.bean.ArticleData
import com.bbgo.module_compose.bean.ArticleDetail
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class ComposeRepository private constructor(private val remoteRepository: ComposeRemoteRepository, private val localRepository: ComposeLocalRepository) {

    companion object {
        @Volatile
        private var repository: ComposeRepository? = null

        fun getInstance(remoteRepository: ComposeRemoteRepository, localRepository: ComposeLocalRepository): ComposeRepository {
            if (repository == null) {
                synchronized(ComposeRepository::class.java) {
                    if (repository == null) {
                        repository = ComposeRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }

    fun getTopArticles() : Flow<HttpResult<List<ArticleDetail>>> {
        return remoteRepository.getTopArticles()
    }

    fun getArticles(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getArticles(pageNum)
    }
}