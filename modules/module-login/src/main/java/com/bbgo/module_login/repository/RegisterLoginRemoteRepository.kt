package com.bbgo.module_login.repository

import com.bbgo.common_base.bean.BaseBean
import com.bbgo.common_base.bean.HttpResult
import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_login.bean.LoginData
import com.bbgo.module_login.net.api.HttpLoginApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class RegisterLoginRemoteRepository @Inject constructor(){

    private val service = ServiceCreators.create(HttpLoginApiService::class.java)

    fun registerWanAndroid(username: String,
                              password: String,
                              repassword: String) : Flow<HttpResult<LoginData>> {
        return service.registerWanAndroid(username, password, repassword)
    }

    fun loginWanAndroid(username: String,
                                password: String) : Flow<HttpResult<LoginData>> {
        return service.loginWanAndroid(username, password)
    }

    fun logout() : Flow<BaseBean> {
        return service.logout()
    }

    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = RegisterLoginRemoteRepository()
    }
}