package com.bbgo.module_home.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bbgo.module_home.bean.Banner
import com.bbgo.module_home.local.dao.BannerDao

/**
 *  author: wangyb
 *  date: 4/8/21 7:56 PM
 *  description: todo
 */
@Database(entities = [Banner::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bannerDao(): BannerDao
}