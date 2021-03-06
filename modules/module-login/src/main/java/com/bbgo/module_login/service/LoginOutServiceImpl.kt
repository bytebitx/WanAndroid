package com.bbgo.module_login.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_service.login.LoginOutService
import com.bbgo.module_login.repository.RegisterLoginLocalRepository
import com.bbgo.module_login.repository.RegisterLoginRemoteRepository
import com.bbgo.module_login.repository.RegisterLoginRepository
import com.bbgo.module_login.viewmodel.RegisterLoginViewModel
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