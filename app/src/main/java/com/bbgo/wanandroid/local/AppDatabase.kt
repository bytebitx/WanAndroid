package com.bbgo.wanandroid.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.wanandroid.bean.WXArticle
import com.bbgo.wanandroid.local.dao.BannerDao

/**
 *  author: wangyb
 *  date: 4/8/21 7:56 PM
 *  description: todo
 */
@Database(entities = [WXArticle::class, Banner::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bannerDao(): BannerDao
}