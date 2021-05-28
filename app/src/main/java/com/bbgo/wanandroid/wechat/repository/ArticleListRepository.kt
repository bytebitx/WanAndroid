package com.bbgo.wanandroid.wechat.repository

import com.bbgo.wanandroid.bean.*
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class ArticleListRepository private constructor(private val remoteRepository: ArticleListRemoteRepository, private val localRepository: ArticleListLocalRepository) {

    companion object {
        private var repository: ArticleListRepository? = null

        fun getInstance(remoteRepository: ArticleListRemoteRepository, localRepository: ArticleListLocalRepository): ArticleListRepository {
            if (repository == null) {
                synchronized(ArticleListRepository::class.java) {
                    if (repository == null) {
                        repository = ArticleListRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }

    suspend fun getWXChapters() : Flow<WXArticles> {
        return remoteRepository.getWXChapters()
    }

    suspend fun getWXArticles(id: Int, page: Int) : Flow<Articles> {
        return remoteRepository.getWXArticles(id, page)
    }

    suspend fun getKnowledgeList(id: Int, page: Int) : Flow<Articles> {
        return remoteRepository.getKnowledgeList(id, page)
    }

    suspend fun getBanners(): Flow<BannerResponse> {
        return remoteRepository.getBanners()
    }

    fun insertWXArticles(articles: List<WXArticle>) {
        localRepository.insertWXArticles(articles)
    }

    fun insertBanners(banners: List<Banner>) {
        localRepository.insertBanners(banners)
    }
}