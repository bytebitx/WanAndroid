package com.bbgo.module_wechat.net

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_wechat.net.api.HttpWeChatApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpWeChatService {
    val service = ServiceCreators.create(HttpWeChatApiService::class.java)
}