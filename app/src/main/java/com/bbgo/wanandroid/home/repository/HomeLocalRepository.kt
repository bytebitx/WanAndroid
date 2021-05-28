package com.bbgo.wanandroid.home.repository

import com.bbgo.wanandroid.artical.local.WXArticleDao
import com.bbgo.wanandroid.bean.Banner
import com.bbgo.wanandroid.local.DBUtil
import com.bbgo.wanandroid.square.repository.SquareLocalRepository
import com.bbgo.wanandroid.square.repository.SquareRepository

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class HomeLocalRepository private constructor(private val WXArticleDao: WXArticleDao){

    fun insertBanners(banners: List<Banner>) {
        DBUtil.getInstance().runInTransaction {
            banners.forEach{ data->
                WXArticleDao.insertBanners(data)
            }
        }
    }



    companion object {
        private var repository: HomeLocalRepository? = null

        fun getInstance(WXArticleDao: WXArticleDao): HomeLocalRepository {
            if (repository == null) {
                synchronized(HomeLocalRepository::class.java) {
                    if (repository == null) {
                        repository = HomeLocalRepository(WXArticleDao)
                    }
                }
            }
            return repository!!
        }
    }
}