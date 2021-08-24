package com.bbgo.module_home.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.module_home.bean.ArticleData
import com.bbgo.module_home.bean.ArticleDetail
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class HomeRepository private constructor(private val remoteRepository: HomeRemoteRepository, private val localRepository: HomeLocalRepository) {

    companion object {
        @Volatile
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

    suspend fun getTopArticles() : Flow<HttpResult<List<ArticleDetail>>> {
        return remoteRepository.getTopArticles()
    }

    suspend fun getArticles(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getArticles(pageNum)
    }

    suspend fun getBanners(): Flow<HttpResult<List<Banner>>> {
        return remoteRepository.getBanners()
    }
}