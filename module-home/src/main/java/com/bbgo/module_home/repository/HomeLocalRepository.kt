package com.bbgo.module_home.repository

import com.bbgo.module_home.bean.Banner


/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class HomeLocalRepository private constructor(){

    fun insertBanners(banners: List<Banner>) {
    }



    companion object {
        private var repository: HomeLocalRepository? = null

        fun getInstance(): HomeLocalRepository {
            if (repository == null) {
                synchronized(HomeLocalRepository::class.java) {
                    if (repository == null) {
                        repository = HomeLocalRepository()
                    }
                }
            }
            return repository!!
        }
    }
}