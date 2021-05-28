package com.bbgo.wanandroid.wechat.repository

import com.bbgo.wanandroid.artical.local.WXArticleDao
import com.bbgo.wanandroid.bean.Banner
import com.bbgo.wanandroid.bean.WXArticle
import com.bbgo.wanandroid.local.DBUtil

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class ArticleListLocalRepository private constructor(private val WXArticleDao: WXArticleDao){

    fun insertWXArticles(articles: List<WXArticle>) {
        DBUtil.getInstance().runInTransaction {
            articles.forEach{ data->
                WXArticleDao.insertAll(data)
            }
        }
    }

    fun insertBanners(banners: List<Banner>) {
        DBUtil.getInstance().runInTransaction {
            banners.forEach{ data->
                WXArticleDao.insertBanners(data)
            }
        }
    }



    companion object {
        private var repository: ArticleListLocalRepository? = null

        fun getInstance(WXArticleDao: WXArticleDao): ArticleListLocalRepository {
            if (repository == null) {
                synchronized(ArticleListLocalRepository::class.java) {
                    if (repository == null) {
                        repository = ArticleListLocalRepository(WXArticleDao)
                    }
                }
            }
            return repository!!
        }
    }
}