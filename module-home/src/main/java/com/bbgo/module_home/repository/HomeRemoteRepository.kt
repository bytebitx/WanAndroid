package com.bbgo.module_home.repository

import com.bbgo.common_service.banner.bean.BannerResponse
import com.bbgo.module_home.bean.Articles
import com.bbgo.module_home.bean.TopArticles
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


    suspend fun getTopArticles() : Flow<TopArticles> {
        return flow {
            emit(HttpHomeService.service.getTopArticles())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getArticles(pageNum: Int) : Flow<Articles> {
        return flow {
            emit(HttpHomeService.service.getArticles(pageNum))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getBanners(): Flow<BannerResponse> {
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