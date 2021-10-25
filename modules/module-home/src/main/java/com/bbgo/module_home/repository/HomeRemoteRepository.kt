package com.bbgo.module_home.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_home.bean.ArticleData
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.bean.Banner
import com.bbgo.module_home.net.api.HttpHomeApiService
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