package com.bbgo.module_login.util

import com.bbgo.module_login.repository.RegisterLoginLocalRepository
import com.bbgo.module_login.repository.RegisterLoginRemoteRepository
import com.bbgo.module_login.repository.RegisterLoginRepository
import com.bbgo.module_login.viewmodel.RegisterLoginViewModelFactory

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {


    fun getLoginViewModelFactory() = RegisterLoginViewModelFactory(
        RegisterLoginRepository.getInstance(
        RegisterLoginRemoteRepository.instance, RegisterLoginLocalRepository.getInstance()))
}