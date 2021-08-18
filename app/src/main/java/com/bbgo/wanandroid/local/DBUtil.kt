package com.bbgo.wanandroid.local

import androidx.room.Room
import com.bbgo.common_base.BaseApplication

/**
 *  author: wangyb
 *  date: 2021/5/24 5:56 下午
 *  description: todo
 */
class DBUtil {

    companion object {

        @Volatile
        private var db: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return db ?: synchronized(AppDatabase::class.java) {
                val roomDB = Room.databaseBuilder(
                    BaseApplication.getContext().applicationContext,
                    AppDatabase::class.java, "WanAndroid.db").build()
                db = roomDB
                roomDB
            }
        }
    }
}