package com.bbgo.module_wechat.bean

/**
 *  author: wangyb
 *  date: 4/11/21 6:28 PM
 *  description: todo
 */

data class WXArticles(
        val `data`: List<WXArticle>,
        val errorCode: Int = 0,
        val errorMsg: String = "",
)

data class WXArticle (
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
)

