package com.bbgo.wanandroid.login.repository

import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Mmkv
import com.bbgo.wanandroid.artical.local.WXArticleDao
import com.bbgo.wanandroid.bean.LoginData

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class RegisterLoginLocalRepository private constructor(private val wxArticleDao: WXArticleDao){


    fun insertLoginData(userName: String) {
        Mmkv.encode(Constants.USER_NAME, userName)
    }

    companion object {
        private var repository: RegisterLoginLocalRepository? = null

        fun getInstance(wxArticleDao: WXArticleDao): RegisterLoginLocalRepository {
            if (repository == null) {
                synchronized(RegisterLoginRepository::class.java) {
                    if (repository == null) {
                        repository = RegisterLoginLocalRepository(wxArticleDao)
                    }
                }
            }
            return repository!!
        }
    }
}