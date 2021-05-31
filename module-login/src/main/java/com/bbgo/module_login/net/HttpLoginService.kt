package com.bbgo.module_login.net

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_login.net.api.HttpLoginApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpLoginService {
    val service = ServiceCreators.create(HttpLoginApiService::class.java)
}