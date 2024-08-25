package com.bytebitx.login.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.service.login.LoginOutService
import com.bytebitx.login.repository.RegisterLoginLocalRepository
import com.bytebitx.login.repository.RegisterLoginRemoteRepository
import com.bytebitx.login.repository.RegisterLoginRepository
import com.bytebitx.login.viewmodel.RegisterLoginViewModel
import kotlinx.coroutines.flow.Flow

/**
 *  author: wangyb
 *  date: 2021/5/31 2:25 下午
 *  description: todo
 */
@Route(path = RouterPath.LoginRegister.SERVICE_LOGOUT)
class LoginOutServiceImpl : LoginOutService{

    override suspend fun logOut(): Flow<String> {
        val viewModel = RegisterLoginViewModel(RegisterLoginRepository.getInstance(RegisterLoginRemoteRepository.instance,
            RegisterLoginLocalRepository.getInstance()))
        return viewModel.logOutToMain()
    }

    override fun init(context: Context?) {
    }
}