package com.bytebitx.compose.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.base.net.ServiceCreators
import com.bytebitx.compose.bean.ArticleData
import com.bytebitx.compose.bean.ArticleDetail
import com.bytebitx.compose.net.api.HttpComposeApiService
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