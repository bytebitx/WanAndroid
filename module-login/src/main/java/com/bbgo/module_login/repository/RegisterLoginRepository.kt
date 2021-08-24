package com.bbgo.module_login.repository

import com.bbgo.common_base.bean.BaseBean
import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_login.bean.LoginData
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class RegisterLoginRepository private constructor(private val remoteRepository: RegisterLoginRemoteRepository, private val localRepository: RegisterLoginLocalRepository) {

    companion object {
        @Volatile
        private var repository: RegisterLoginRepository? = null

        fun getInstance(remoteRepository: RegisterLoginRemoteRepository, localRepository: RegisterLoginLocalRepository): RegisterLoginRepository {
            if (repository == null) {
                synchronized(RegisterLoginRepository::class.java) {
                    if (repository == null) {
                        repository = RegisterLoginRepository(remoteRepository, localRepository)
                    }
                }
            }
            return repository!!
        }
    }

    suspend fun registerWanAndroid(username: String,
                                   password: String,
                                   repassword: String) : Flow<HttpResult<LoginData>> {
        return remoteRepository.registerWanAndroid(username, password, repassword)
    }

    suspend fun loginWanAndroid(username: String,
                                password: String) : Flow<HttpResult<LoginData>> {
        return remoteRepository.loginWanAndroid(username, password)
    }

    suspend fun logout() : Flow<BaseBean> {
        return remoteRepository.logout()
    }

    fun insertLoginData(userName: String) {
        localRepository.insertLoginData(userName)
    }
}