package com.bbgo.wanandroid.bean

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 *  author: wangyb
 *  date: 2021/5/21 10:26 上午
 *  description: todo
 */

data class LoginRegisterResponse (
    val `data`: LoginData,
    val errorCode: Int = 0,
    val errorMsg: String = "",
)

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