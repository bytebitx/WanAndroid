package com.bytebitx.login.repository

import com.bytebitx.base.constants.Constants
import com.bytebitx.base.ext.Prefs
import com.bytebitx.login.bean.LoginData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
@ActivityRetainedScoped
class RegisterLoginLocalRepository @Inject constructor(){


    fun insertLoginData(loginData: LoginData?) {
        if (loginData == null) return
        Prefs.putString(Constants.USER_NAME, loginData.username)
        Prefs.putString(Constants.USER_ID, loginData.id.toString())
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