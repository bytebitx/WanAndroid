package com.bbgo.module_compose.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_compose.bean.ArticleData
import com.bbgo.module_compose.bean.ArticleDetail
import com.bbgo.module_compose.net.HttpComposeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
class ComposeRemoteRepository private constructor(){


    suspend fun getTopArticles() : Flow<HttpResult<List<ArticleDetail>>> {
        return flow {
            emit(HttpComposeService.service.getTopArticles())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getArticles(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return flow {
            emit(HttpComposeService.service.getArticles(pageNum))
        }.flowOn(Dispatchers.IO)
    }


    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = ComposeRemoteRepository()
    }
}