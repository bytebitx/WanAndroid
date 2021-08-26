package com.bbgo.module_wechat.repository

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class WxLocalRepository private constructor(){


    companion object {
        @Volatile
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