package com.bbgo.module_wechat.util

import com.bbgo.module_wechat.viewmodel.WeChatViewModelFactory
import com.bbgo.module_wechat.repository.WxLocalRepository
import com.bbgo.module_wechat.repository.WxRemoteRepository
import com.bbgo.module_wechat.repository.WxRepository

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getWeChatViewModelFactory() = WeChatViewModelFactory(
        WxRepository.getInstance(WxRemoteRepository.instance, WxLocalRepository.getInstance()))
}