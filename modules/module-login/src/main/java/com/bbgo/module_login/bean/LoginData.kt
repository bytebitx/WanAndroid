package com.bbgo.module_login.bean

import androidx.annotation.Keep

/**
 *  author: wangyb
 *  date: 2021/5/21 10:26 上午
 *  description: todo
 */
@Keep
data class LoginData(
    val chapterTops: MutableList<String>,
    val collectIds: MutableList<String>,
    val email: String,
    val icon: String,
    val id: Int,
    var password: String,
    val token: String,
    val type: Int,
    val username: String
)