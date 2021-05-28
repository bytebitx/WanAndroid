package com.bbgo.common_service.test

import com.alibaba.android.arouter.facade.template.IProvider
import com.bbgo.common_service.test.bean.TestContentBean

/**
 *  author: wangyb
 *  date: 2021/5/25 10:17 上午
 *  description: todo
 */
interface TestService : IProvider {
    fun insertTest(testBean: TestContentBean)
}