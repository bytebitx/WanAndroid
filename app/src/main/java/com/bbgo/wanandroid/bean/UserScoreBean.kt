package com.bbgo.wanandroid.bean

/**
 *  author: wangyb
 *  date: 2021/7/19 10:57 上午
 *  description: 个人积分实体
 */
data class UserScoreBean(
    val coinCount: Int,
    val date: Long,
    val desc: String,
    val id: Int,
    val reason: String,
    val type: Int,
    val userId: Int,
    val userName: String
)