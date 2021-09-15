package com.bbgo.module_project.local

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
                    AppDatabase::class.java, "project.db")
                    .addMigrations(migration1_2)
                    .build()
                db = roomDB
                roomDB
            }
        }

        private val migration1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE article_detail ADD COLUMN local_path TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}