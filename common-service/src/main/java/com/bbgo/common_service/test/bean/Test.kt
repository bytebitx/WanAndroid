package com.bbgo.common_service.test.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  author: wangyb
 *  date: 2021/5/24 7:27 下午
 *  description: todo
 */
@Entity
data class TestContentBean (
    @PrimaryKey
    var id: Int,
    var name: String
        )