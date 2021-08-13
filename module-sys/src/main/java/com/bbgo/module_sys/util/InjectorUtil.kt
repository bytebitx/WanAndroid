package com.bbgo.module_sys.util

import com.bbgo.module_sys.viewmodel.SysViewModelFactory
import com.bbgo.module_sys.repository.SysLocalRepository
import com.bbgo.module_sys.repository.SysRemoteRepository
import com.bbgo.module_sys.repository.SysRepository

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getSysViewModelFactory() = SysViewModelFactory(
        SysRepository.getInstance(SysRemoteRepository.instance, SysLocalRepository.getInstance()))
}