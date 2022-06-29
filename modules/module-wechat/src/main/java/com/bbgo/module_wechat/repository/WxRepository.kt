package com.bbgo.module_wechat.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_wechat.bean.ArticleData
import com.bbgo.module_wechat.bean.WXArticle
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
@ActivityRetainedScoped
class WxRepository @Inject constructor(private val remoteRepository: WxRemoteRepository, private val localRepository: WxLocalRepository) {

    fun getWXChapters() : Flow<HttpResult<List<WXArticle>>> {
        return remoteRepository.getWXChapters()
    }

    fun getWXArticles(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getWXArticles(id, page)
    }

    fun getKnowledgeList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getKnowledgeList(id, page)
    }
}