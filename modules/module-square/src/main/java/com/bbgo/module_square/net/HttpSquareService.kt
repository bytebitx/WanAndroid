package com.bbgo.module_square.net

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_square.net.api.HttpSquareApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpSquareService {
    val service = ServiceCreators.create(HttpSquareApiService::class.java)
}