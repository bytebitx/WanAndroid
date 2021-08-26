package com.bbgo.module_compose.net

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_compose.net.api.HttpComposeApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpComposeService {
    val service = ServiceCreators.create(HttpComposeApiService::class.java)
}