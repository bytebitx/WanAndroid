package com.bytebitx.service.collect

import com.alibaba.android.arouter.facade.template.IProvider

/**
 *  author: wangyb
 *  date: 2021/5/27 7:25 下午
 *  description: todo
 */
interface CollectService : IProvider {
    fun collect (indexPage: Int, position: Int, pageId: Int)
    fun unCollect (indexPage: Int, position: Int,pageId: Int)
}