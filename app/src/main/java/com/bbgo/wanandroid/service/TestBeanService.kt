package com.bbgo.wanandroid.service

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.ext.logD
import com.bbgo.common_service.test.TestService
import com.bbgo.common_service.test.bean.TestContentBean
import com.bbgo.wanandroid.local.DBUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 2021/5/25 10:21 上午
 *  description: todo
 */
@Route(path = "/app/TestService", name="测试服务")
class TestBeanService : TestService {

    private val job by lazy { Job() }

    override fun insertTest(testBean: TestContentBean) {
        logD("insertTest === $testBean")

        CoroutineScope(job + Dispatchers.IO).launch {
            DBUtil.getInstance().testDao().insertAll(testBean)
        }
    }

    override fun init(context: Context?) {
    }
}