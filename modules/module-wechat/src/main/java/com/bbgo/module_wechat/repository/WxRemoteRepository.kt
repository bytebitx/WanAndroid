package com.bbgo.module_wechat.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_wechat.bean.ArticleData
import com.bbgo.module_wechat.bean.WXArticle
import com.bbgo.module_wechat.net.HttpWeChatService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class WxRemoteRepository @Inject constructor(){

    fun getWXChapters() : Flow<HttpResult<List<WXArticle>>> {
        return HttpWeChatService.service.getWXChapters()
    }

    fun getWXArticles(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return HttpWeChatService.service.getWXArticles(id, page)
    }

    fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return HttpWeChatService.service.getKnowledgeList(id, page)
    }

    /********************静态内部类单例***************************/
    /*companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = WxRemoteRepository()
    }*/
}