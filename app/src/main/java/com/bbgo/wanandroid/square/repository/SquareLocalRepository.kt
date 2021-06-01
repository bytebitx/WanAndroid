package com.bbgo.wanandroid.square.repository

import com.bbgo.wanandroid.local.dao.WXArticleDao
import com.bbgo.wanandroid.bean.Banner
import com.bbgo.wanandroid.local.DBUtil

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class SquareLocalRepository private constructor(private val WXArticleDao: WXArticleDao){

    fun insertBanners(banners: List<Banner>) {
        DBUtil.getInstance().runInTransaction {
            banners.forEach{ data->
                WXArticleDao.insertBanners(data)
            }
        }
    }



    companion object {
        private var repository: SquareLocalRepository? = null

        fun getInstance(WXArticleDao: WXArticleDao): SquareLocalRepository {
            if (repository == null) {
                synchronized(SquareRepository::class.java) {
                    if (repository == null) {
                        repository = SquareLocalRepository(WXArticleDao)
                    }
                }
            }
            return repository!!
        }
    }
}