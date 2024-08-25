package com.bytebitx.project.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bytebitx.project.bean.ArticleDetail
import com.bytebitx.project.bean.ProjectBean
import com.bytebitx.project.bean.Tag
import com.bytebitx.project.local.dao.ArticleDetailDao
import com.bytebitx.project.local.dao.ProjectTreeDao
import com.bytebitx.project.local.dao.TagDao

/**
 *  author: wangyb
 *  date: 4/8/21 7:56 PM
 *  description: todo
 */
@Database(entities = [ProjectBean::class, ArticleDetail::class, Tag::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectTreeDao(): ProjectTreeDao
    abstract fun articleDetailDao(): ArticleDetailDao
    abstract fun tagDao(): TagDao
}