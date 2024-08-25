package com.bytebitx.home.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.home.bean.ArticleData
import com.bytebitx.home.bean.ArticleDetail
import com.bytebitx.home.bean.Banner
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