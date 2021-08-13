package com.bbgo.module_sys.net

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_sys.net.api.HttpSysApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpSysService {
    val service = ServiceCreators.create(HttpSysApiService::class.java)
}