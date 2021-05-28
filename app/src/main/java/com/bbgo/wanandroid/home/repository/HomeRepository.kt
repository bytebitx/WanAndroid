package com.bbgo.wanandroid.home.repository

import com.bbgo.wanandroid.bean.Articles
import com.bbgo.wanandroid.bean.Banner
import com.bbgo.wanandroid.bean.BannerResponse
import com.bbgo.wanandroid.bean.TopArticles
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class HomeRepository private constructor(private val remoteRepository: HomeRemoteRepository, private val localRepository: HomeLocalRepository) {

    companion object {
        private var repository: HomeRepository? = null

        fun getInstance(remoteRepository: HomeRemoteRepository, localRepository: HomeLocalRepository): HomeRepository {
            if (repository == null) {
                synchronized(HomeRepository::class.java) {
                    if (repository == null) {
                        repository = HomeRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }

    suspend fun getTopArticles() : Flow<TopArticles> {
        return remoteRepository.getTopArticles()
    }

    suspend fun getArticles(pageNum: Int) : Flow<Articles> {
        return remoteRepository.getArticles(pageNum)
    }

    suspend fun getBanners(): Flow<BannerResponse> {
        return remoteRepository.getBanners()
    }

    fun insertBanners(banners: List<Banner>) {
        localRepository.insertBanners(banners)
    }
}