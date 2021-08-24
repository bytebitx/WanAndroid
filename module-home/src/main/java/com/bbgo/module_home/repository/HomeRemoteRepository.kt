package com.bbgo.module_home.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.module_home.bean.ArticleData
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.net.HttpHomeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
class HomeRemoteRepository private constructor(){


    suspend fun getTopArticles() : Flow<HttpResult<List<ArticleDetail>>> {
        return flow {
            emit(HttpHomeService.service.getTopArticles())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getArticles(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return flow {
            emit(HttpHomeService.service.getArticles(pageNum))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getBanners(): Flow<HttpResult<List<Banner>>> {
        return flow {
            emit(HttpHomeService.service.getBanners())
        }.flowOn(Dispatchers.IO)
    }

    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = HomeRemoteRepository()
    }
}