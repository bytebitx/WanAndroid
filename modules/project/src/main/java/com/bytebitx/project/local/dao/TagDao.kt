package com.bytebitx.project.local.dao

import androidx.room.*
import com.bytebitx.project.bean.ProjectBean
import com.bytebitx.project.bean.Tag
import kotlinx.coroutines.flow.Flow

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/25 3:34 下午
 */
@Dao
interface TagDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: Tag)

    @Transaction
    @Query("SELECT * FROM TAG")
    fun getTagList() : Flow<List<Tag>>

}