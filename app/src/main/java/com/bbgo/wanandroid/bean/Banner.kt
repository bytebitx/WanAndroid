package com.bbgo.wanandroid.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

data class BannerResponse (
    val `data`: List<Banner>,
    val errorCode: Int = 0,
    val errorMsg: String = "",
)

@Entity
data class Banner(
    val desc: String,
    @PrimaryKey
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)