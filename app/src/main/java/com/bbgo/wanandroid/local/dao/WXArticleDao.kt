package com.bbgo.wanandroid.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bbgo.wanandroid.bean.Banner
import com.bbgo.wanandroid.bean.WXArticle

/**
 *  author: wangyb
 *  date: 4/8/21 7:57 PM
 *  description: todo
 */
@Dao
interface WXArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg data: WXArticle)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanners(vararg data: Banner)
}