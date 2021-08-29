package com.bbgo.module_wechat.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_wechat.bean.ArticleData
import com.bbgo.module_wechat.bean.WXArticle
import com.bbgo.module_wechat.net.HttpWeChatService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class WxRemoteRepository @Inject constructor(){

    suspend fun getWXChapters() : Flow<HttpResult<List<WXArticle>>> {
        return flow {
            emit(HttpWeChatService.service.getWXChapters())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getWXArticles(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return flow {
            emit(HttpWeChatService.service.getWXArticles(id, page))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return flow {
            emit(HttpWeChatService.service.getKnowledgeList(id, page))
        }.flowOn(Dispatchers.IO)
    }

    /********************静态内部类单例***************************/
    /*companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = WxRemoteRepository()
    }*/
}