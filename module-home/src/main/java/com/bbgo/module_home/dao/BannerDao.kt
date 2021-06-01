package com.bbgo.module_home.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bbgo.module_home.bean.Banner

/**
 *  author: wangyb
 *  date: 2021/5/31 8:05 下午
 *  description: todo
 */
@Dao
interface BannerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanners(vararg data: Banner)
}