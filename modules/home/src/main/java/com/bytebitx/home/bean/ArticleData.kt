package com.bytebitx.home.bean

import androidx.annotation.Keep

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/10/22 8:59 下午
 */
@Keep
data class ArticleData(
    val curPage: Int,
    val datas: MutableList<ArticleDetail>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)