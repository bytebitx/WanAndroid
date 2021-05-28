package com.bbgo.wanandroid.collect.repository

import com.bbgo.wanandroid.bean.CollectBean
import com.bbgo.wanandroid.network.HttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

    suspend fun collectArticle(id: Int) : Flow<CollectBean> {
        return flow {
            emit(HttpService.service.addCollectArticle(id))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun unCollectArticle(id: Int) : Flow<CollectBean> {
        return flow {
            emit(HttpService.service.cancelCollectArticle(id))
        }.flowOn(Dispatchers.IO)
    }
}