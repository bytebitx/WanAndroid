package com.bbgo.wanandroid.wechat.repository

import com.bbgo.wanandroid.bean.Articles
import com.bbgo.wanandroid.bean.BannerResponse
import com.bbgo.wanandroid.bean.WXArticles
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
class WxRemoteRepository private constructor(){

    suspend fun getWXChapters() : Flow<WXArticles> {
        return flow {
            emit(HttpService.service.getWXChapters())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getWXArticles(id: Int, page: Int) : Flow<Articles> {
        return flow {
            emit(HttpService.service.getWXArticles(id, page))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<Articles> {
        return flow {
            emit(HttpService.service.getKnowledgeList(id, page))
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
        val holder = WxRemoteRepository()
    }
}