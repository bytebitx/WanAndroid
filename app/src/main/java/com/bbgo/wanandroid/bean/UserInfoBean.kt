package com.bbgo.wanandroid.bean

data class UserInfoBean(
    val `data`: UserInfo,
    val errorCode: Int,
    val errorMsg: String


)

data class UserInfo(
    val coinCount: Int,
    val rank: Int,
    val userId: Int,
    val username: String
)