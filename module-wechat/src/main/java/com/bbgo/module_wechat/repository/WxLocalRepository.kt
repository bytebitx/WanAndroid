package com.bbgo.module_wechat.repository

import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.module_wechat.bean.WXArticle

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class WxLocalRepository private constructor(){

    fun insertWXArticles(articles: List<WXArticle>) {
    }

    fun insertBanners(banners: List<Banner>) {

    }



    companion object {
        private var repository: WxLocalRepository? = null

        fun getInstance(): WxLocalRepository {
            if (repository == null) {
                synchronized(WxRepository::class.java) {
                    if (repository == null) {
                        repository = WxLocalRepository()
                    }
                }
            }
            return repository!!
        }
    }
}