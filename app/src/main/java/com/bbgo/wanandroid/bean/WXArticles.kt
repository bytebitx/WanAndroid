package com.bbgo.wanandroid.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Entity
data class WXArticle (
        val courseId: Int,
        @PrimaryKey
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
)

