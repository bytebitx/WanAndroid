package com.bbgo.wanandroid.bean

/**
 *  author: wangyb
 *  date: 2021/7/19 10:54 上午
 *  description: todo
 */
data class BaseListResponseBean<T>(
    val curPage: Int,
    val datas: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)