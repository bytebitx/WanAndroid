package com.bbgo.common_service.collect

import com.alibaba.android.arouter.facade.template.IProvider

/**
 *  author: wangyb
 *  date: 2021/5/27 7:25 下午
 *  description: todo
 */
interface CollectService : IProvider {
    fun collect (position: Int, pageId: Int)
    fun unCollect (position: Int,pageId: Int)
}