package com.bbgo.wanandroid.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.wanandroid.bean.UserInfo
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

    suspend fun getUserInfo() : Flow<HttpResult<UserInfo>> {
        return flow {
            emit(HttpService.service.getUserInfo())
        }.flowOn(Dispatchers.IO)
    }

}