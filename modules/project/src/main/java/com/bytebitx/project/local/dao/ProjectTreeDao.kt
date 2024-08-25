package com.bytebitx.project.local.dao

import androidx.room.*
import com.bytebitx.project.bean.ProjectBean
import kotlinx.coroutines.flow.Flow

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/25 3:34 下午
 */
@Dao
interface ProjectTreeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: ProjectBean)

    @Transaction
    @Query("SELECT * FROM PROJECT_TREE")
    fun getProjectTree() : Flow<List<ProjectBean>>

}