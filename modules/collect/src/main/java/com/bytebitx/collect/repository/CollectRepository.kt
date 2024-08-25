package com.bytebitx.collect.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.base.net.ServiceCreators
import com.bytebitx.collect.bean.CollectBean
import com.bytebitx.collect.net.api.HttpApiService
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class CollectRepository private constructor() {

    companion object {
        @Volatile
        private var repository: CollectRepository? = null

        fun getInstance(): CollectRepository {
            if (repository == null) {
                synchronized(CollectRepository::class.java) {
                    if (repository == null) {
                        repository = CollectRepository()
                    }
                }
            }
            return repository!!
        }
    }

    private val service = ServiceCreators.create(HttpApiService::class.java)

    fun collectArticle(id: Int) : Flow<HttpResult<CollectBean>> {
        return service.addCollectArticle(id)
    }

    fun unCollectArticle(id: Int) : Flow<HttpResult<CollectBean>> {
        return service.cancelCollectArticle(id)
    }
}