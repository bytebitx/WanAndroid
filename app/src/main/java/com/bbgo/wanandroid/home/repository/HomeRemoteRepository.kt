package com.bbgo.wanandroid.home.repository

import com.bbgo.wanandroid.bean.Articles
import com.bbgo.wanandroid.bean.BannerResponse
import com.bbgo.wanandroid.bean.TopArticles
import com.bbgo.wanandroid.network.HttpService
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
            emit(HttpService.service.getTopArticles())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getArticles(pageNum: Int) : Flow<Articles> {
        return flow {
            emit(HttpService.service.getArticles(pageNum))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getBanners(): Flow<BannerResponse> {
        return flow {
            emit(HttpService.service.getBanners())
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