package com.bbgo.module_login.repository

import com.bbgo.common_base.bean.BaseBean
import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_login.bean.LoginData
import com.bbgo.module_login.net.HttpLoginService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class RegisterLoginRemoteRepository @Inject constructor(){

    fun registerWanAndroid(username: String,
                              password: String,
                              repassword: String) : Flow<HttpResult<LoginData>> {
        return HttpLoginService.service.registerWanAndroid(username, password, repassword)
    }

    fun loginWanAndroid(username: String,
                                password: String) : Flow<HttpResult<LoginData>> {
        return HttpLoginService.service.loginWanAndroid(username, password)
    }

    fun logout() : Flow<BaseBean> {
        return HttpLoginService.service.logout()
    }

    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = RegisterLoginRemoteRepository()
    }
}