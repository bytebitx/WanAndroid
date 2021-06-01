package com.bbgo.wanandroid.wechat.repository

import com.bbgo.wanandroid.local.dao.WXArticleDao
import com.bbgo.wanandroid.bean.Banner
import com.bbgo.wanandroid.bean.WXArticle
import com.bbgo.wanandroid.local.DBUtil

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class WxLocalRepository private constructor(private val WXArticleDao: WXArticleDao){

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
        private var repository: WxLocalRepository? = null

        fun getInstance(WXArticleDao: WXArticleDao): WxLocalRepository {
            if (repository == null) {
                synchronized(WxRepository::class.java) {
                    if (repository == null) {
                        repository = WxLocalRepository(WXArticleDao)
                    }
                }
            }
            return repository!!
        }
    }
}