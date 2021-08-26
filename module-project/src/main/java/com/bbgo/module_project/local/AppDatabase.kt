package com.bbgo.module_project.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bbgo.module_project.bean.ArticleDetail
import com.bbgo.module_project.bean.ProjectBean
import com.bbgo.module_project.bean.Tag
import com.bbgo.module_project.local.dao.ArticleDetailDao
import com.bbgo.module_project.local.dao.ProjectTreeDao
import com.bbgo.module_project.local.dao.TagDao

/**
 *  author: wangyb
 *  date: 4/8/21 7:56 PM
 *  description: todo
 */
@Database(entities = [ProjectBean::class, ArticleDetail::class, Tag::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectTreeDao(): ProjectTreeDao
    abstract fun articleDetailDao(): ArticleDetailDao
    abstract fun tagDao(): TagDao
}