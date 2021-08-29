package com.bbgo.module_wechat.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_wechat.bean.ArticleData
import com.bbgo.module_wechat.bean.WXArticle
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
@ActivityRetainedScoped
class WxRepository @Inject constructor(private val remoteRepository: WxRemoteRepository, private val localRepository: WxLocalRepository) {

    /*companion object {
        @Volatile
        private var repository: WxRepository? = null

        fun getInstance(remoteRepository: WxRemoteRepository, localRepository: WxLocalRepository): WxRepository {
            if (repository == null) {
                synchronized(WxRepository::class.java) {
                    if (repository == null) {
                        repository = WxRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }*/

    suspend fun getWXChapters() : Flow<HttpResult<List<WXArticle>>> {
        return remoteRepository.getWXChapters()
    }

    suspend fun getWXArticles(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getWXArticles(id, page)
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getKnowledgeList(id, page)
    }
}