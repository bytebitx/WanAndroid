package com.bbgo.module_login.repository

import com.bbgo.common_base.bean.BaseBean
import com.bbgo.module_login.bean.LoginRegisterResponse
import com.bbgo.module_login.net.HttpLoginService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
class RegisterLoginRemoteRepository private constructor(){

    suspend fun registerWanAndroid(username: String,
                              password: String,
                              repassword: String) : Flow<LoginRegisterResponse> {
        return flow {
            emit(HttpLoginService.service.registerWanAndroid(username, password, repassword))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loginWanAndroid(username: String,
                                password: String) : Flow<LoginRegisterResponse> {
        return flow {
            emit(HttpLoginService.service.loginWanAndroid(username, password))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun logout() : Flow<BaseBean> {
        return flow {
            emit(HttpLoginService.service.logout())
        }.flowOn(Dispatchers.IO)
    }

    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = RegisterLoginRemoteRepository()
    }
}