package com.bytebitx.project.di

import android.app.Application
import androidx.room.Room
import com.bytebitx.base.BaseApplication
import com.bytebitx.project.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/30 4:29 下午
 */

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(app: Application) : AppDatabase {
        return Room.databaseBuilder(
            BaseApplication.getContext().applicationContext,
            AppDatabase::class.java, "project.db").build()
    }

    @Provides
    @Singleton
    fun provideProjectTreeDao(database: AppDatabase) = database.projectTreeDao()

    @Provides
    @Singleton
    fun provideArticleDetailDao(database: AppDatabase) = database.articleDetailDao()

    @Provides
    @Singleton
    fun provideTagDao(database: AppDatabase) = database.tagDao()
}