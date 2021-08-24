package com.bbgo.common_service.banner.bean

import androidx.room.Entity
import androidx.room.PrimaryKey


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