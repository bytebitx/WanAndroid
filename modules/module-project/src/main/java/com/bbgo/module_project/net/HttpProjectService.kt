package com.bbgo.module_project.net

import com.bbgo.common_base.net.ServiceCreators
import com.bbgo.module_project.net.api.HttpProjectApiService

/**
 *  author: wangyb
 *  date: 2021/5/26 8:29 下午
 *  description: todo
 */
object HttpProjectService {
    val service = ServiceCreators.create(HttpProjectApiService::class.java)
}