package com.bbgo.module_wechat.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_wechat.bean.ArticleData
import com.bbgo.module_wechat.bean.WXArticle
import com.bbgo.module_wechat.net.api.HttpWeChatApiService
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