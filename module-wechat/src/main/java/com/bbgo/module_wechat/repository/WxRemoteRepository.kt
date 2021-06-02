package com.bbgo.module_wechat.repository

import com.bbgo.module_wechat.bean.Articles
import com.bbgo.module_wechat.bean.WXArticles
import com.bbgo.module_wechat.net.HttpWeChatService
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
            emit(HttpWeChatService.service.getWXChapters())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getWXArticles(id: Int, page: Int) : Flow<Articles> {
        return flow {
            emit(HttpWeChatService.service.getWXArticles(id, page))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<Articles> {
        return flow {
            emit(HttpWeChatService.service.getKnowledgeList(id, page))
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