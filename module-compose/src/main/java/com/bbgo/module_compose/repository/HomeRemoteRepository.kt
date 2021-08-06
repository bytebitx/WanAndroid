package com.bbgo.module_compose.repository

import com.bbgo.module_compose.net.HttpHomeService
import com.bbgo.module_compose.bean.Articles
import com.bbgo.module_compose.bean.TopArticles
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


    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = HomeRemoteRepository()
    }
}