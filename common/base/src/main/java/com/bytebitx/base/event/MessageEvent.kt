package com.bytebitx.base.event

import androidx.annotation.Keep

/**
 *  author: wangyb
 *  date: 2021/5/27 8:12 下午
 *  description: todo
 */
@Keep
data class MessageEvent(val indexPage: Int, val type: String, val position: Int, val pageId: Int)