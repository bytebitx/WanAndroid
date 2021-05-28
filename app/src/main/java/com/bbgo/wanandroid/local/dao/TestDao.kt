package com.bbgo.wanandroid.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.bbgo.common_service.test.bean.TestContentBean

/**
 *  author: wangyb
 *  date: 4/8/21 7:57 PM
 *  description: todo
 */
@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg data: TestContentBean)
}