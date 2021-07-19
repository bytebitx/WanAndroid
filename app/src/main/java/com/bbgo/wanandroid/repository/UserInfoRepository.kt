package com.bbgo.wanandroid.repository

import com.bbgo.common_base.bean.BaseBean
import com.bbgo.module_login.bean.LoginRegisterResponse
import com.bbgo.wanandroid.bean.UserInfoBean
import com.bbgo.wanandroid.network.HttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
class UserInfoRepository private constructor() {

    companion object {
        @Volatile
        private var repository: UserInfoRepository? = null

        fun getInstance(): UserInfoRepository {
            if (repository == null) {
                synchronized(UserInfoRepository::class.java) {
                    if (repository == null) {
                        repository = UserInfoRepository()
                    }
                }
            }
            return repository!!
        }
    }

    suspend fun getUserInfo() : Flow<UserInfoBean> {
        return flow {
            emit(HttpService.service.getUserInfo())
        }.flowOn(Dispatchers.IO)
    }

}