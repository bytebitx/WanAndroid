package com.bbgo.module_home.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_home.bean.ArticleData
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.bean.Banner
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
@ActivityRetainedScoped
class HomeRepository @Inject constructor(private val remoteRepository: HomeRemoteRepository, private val localRepository: HomeLocalRepository) {

    fun getTopArticles() : Flow<HttpResult<List<ArticleDetail>>> {
        return remoteRepository.getTopArticles()
    }

    fun getArticles(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getArticles(pageNum)
    }

    fun getBanners(): Flow<HttpResult<List<Banner>>> {
        return remoteRepository.getBanners()
    }

    fun insertBanners(banners: List<Banner>) {
        localRepository.insertBanners(banners)
    }
}