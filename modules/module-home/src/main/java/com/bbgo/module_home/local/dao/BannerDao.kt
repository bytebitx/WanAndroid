package com.bbgo.module_home.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bbgo.module_home.bean.Banner

/**
 *  author: wangyb
 *  date: 2021/6/2 11:07 上午
 *  description: todo
 */
@Dao
interface BannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg data: Banner)
}