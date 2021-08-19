package com.bbgo.module_wechat.repository

import com.bbgo.module_wechat.bean.Articles
import com.bbgo.module_wechat.bean.WXArticles
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class WxRepository private constructor(private val remoteRepository: WxRemoteRepository, private val localRepository: WxLocalRepository) {

    companion object {
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
    }

    suspend fun getWXChapters() : Flow<WXArticles> {
        return remoteRepository.getWXChapters()
    }

    suspend fun getWXArticles(id: Int, page: Int) : Flow<Articles> {
        return remoteRepository.getWXArticles(id, page)
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<Articles> {
        return remoteRepository.getKnowledgeList(id, page)
    }
}