package com.bbgo.wanandroid.login.repository

import com.bbgo.wanandroid.bean.BaseBean
import com.bbgo.wanandroid.bean.LoginRegisterResponse
import com.bbgo.wanandroid.network.HttpService
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
            emit(HttpService.service.registerWanAndroid(username, password, repassword))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun loginWanAndroid(username: String,
                                password: String) : Flow<LoginRegisterResponse> {
        return flow {
            emit(HttpService.service.loginWanAndroid(username, password))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun logout() : Flow<BaseBean> {
        return flow {
            emit(HttpService.service.logout())
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