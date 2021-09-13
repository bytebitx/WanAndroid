package com.bbgo.module_collect.network

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_collect.network.api.HttpApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpService {
    val service = ServiceCreators.create(HttpApiService::class.java)
}