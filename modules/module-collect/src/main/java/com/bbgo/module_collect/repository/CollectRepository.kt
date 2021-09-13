package com.bbgo.module_collect.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_collect.bean.CollectBean
import com.bbgo.module_collect.network.HttpService
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class CollectRepository private constructor() {

    companion object {
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

    fun collectArticle(id: Int) : Flow<HttpResult<CollectBean>> {
        return HttpService.service.addCollectArticle(id)
    }

    fun unCollectArticle(id: Int) : Flow<HttpResult<CollectBean>> {
        return HttpService.service.cancelCollectArticle(id)
    }
}