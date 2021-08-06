package com.bbgo.module_compose.net

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_compose.net.api.HttpHomeApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpHomeService {
    val service = ServiceCreators.create(HttpHomeApiService::class.java)
}