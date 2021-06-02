package com.bbgo.module_home.repository

import com.bbgo.common_service.banner.bean.BannerResponse
import com.bbgo.module_home.bean.Articles
import com.bbgo.module_home.bean.TopArticles
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
}