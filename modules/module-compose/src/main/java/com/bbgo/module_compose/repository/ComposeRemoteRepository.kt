package com.bbgo.module_compose.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_compose.bean.ArticleData
import com.bbgo.module_compose.bean.ArticleDetail
import com.bbgo.module_compose.net.api.HttpComposeApiService
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
class ComposeRemoteRepository private constructor(){

    private val service = ServiceCreators.create(HttpComposeApiService::class.java)


    fun getTopArticles() : Flow<HttpResult<List<ArticleDetail>>> {
        return service.getTopArticles()
    }

    fun getArticles(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return service.getArticles(pageNum)
    }


    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = ComposeRemoteRepository()
    }
}