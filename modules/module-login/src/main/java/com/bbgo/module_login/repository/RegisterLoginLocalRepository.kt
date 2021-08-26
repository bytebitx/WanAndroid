package com.bbgo.module_login.repository

import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Prefs

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
class RegisterLoginLocalRepository private constructor(){


    fun insertLoginData(userName: String) {
        Prefs.putString(Constants.USER_NAME, userName)
    }

    companion object {
        @Volatile
        private var repository: RegisterLoginLocalRepository? = null

        fun getInstance(): RegisterLoginLocalRepository {
            if (repository == null) {
                synchronized(RegisterLoginRepository::class.java) {
                    if (repository == null) {
                        repository = RegisterLoginLocalRepository()
                    }
                }
            }
            return repository!!
        }
    }
}