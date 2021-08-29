package com.bbgo.module_wechat.repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
@ActivityRetainedScoped
class WxLocalRepository @Inject constructor(){


    /*companion object {
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
    }*/
}