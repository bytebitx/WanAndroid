package com.bytebitx.home.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.base.net.ServiceCreators
import com.bytebitx.home.bean.ArticleData
import com.bytebitx.home.bean.ArticleDetail
import com.bytebitx.home.bean.Banner
import com.bytebitx.home.net.api.HttpHomeApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class HomeRemoteRepository @Inject constructor(){

    private val service = ServiceCreators.create(HttpHomeApiService::class.java)


    fun getTopArticles() : Flow<HttpResult<List<ArticleDetail>>> {
        return service.getTopArticles()
    }

    fun getArticles(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return service.getArticles(pageNum)
    }

    fun getBanners(): Flow<HttpResult<List<Banner>>> {
        return service.getBanners()
    }
}