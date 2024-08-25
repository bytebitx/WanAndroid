package com.bytebitx.wechat.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.base.net.ServiceCreators
import com.bytebitx.wechat.bean.ArticleData
import com.bytebitx.wechat.bean.WXArticle
import com.bytebitx.wechat.net.api.HttpWeChatApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class WxRemoteRepository @Inject constructor() {

    private val service = ServiceCreators.create(HttpWeChatApiService::class.java)

    fun getWXChapters() : Flow<HttpResult<List<WXArticle>>> {
        return service.getWXChapters()
    }

    fun getWXArticles(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return service.getWXArticles(id, page)
    }

    fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return service.getKnowledgeList(id, page)
    }

    /********************静态内部类单例***************************/
    /*companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = WxRemoteRepository()
    }*/
}