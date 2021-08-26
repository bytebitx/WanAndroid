package com.bbgo.module_project.local.dao

import androidx.room.*
import com.bbgo.module_project.bean.ArticleDetail
import com.bbgo.module_project.bean.ArticleDetailWithTag
import kotlinx.coroutines.flow.Flow

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/25 3:34 下午
 */
@Dao
interface ArticleDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: ArticleDetail)

    /**
     * 只能查询ArticleDetail表
     */
    @Transaction
    @Query("SELECT * FROM ARTICLE_DETAIL")
    fun getArticleDetailList() : Flow<MutableList<ArticleDetail>>

    /**
     * 可将ArticleDetail表和TAG表一起联合查询
     * 该方法需要 Room 运行两次查询，因此应向该方法添加 @Transaction 注释，以确保整个操作以原子方式执行
     */
    @Transaction
    @Query("SELECT * FROM ARTICLE_DETAIL ORDER BY id ASC")
    fun getArticleDetailWithTag() : Flow<MutableList<ArticleDetailWithTag>>

    @Transaction
    @Query("DELETE FROM ARTICLE_DETAIL WHERE id=:articleId")
    fun deleteArticleById(articleId: String)

    @Delete
    fun deleteArticle(vararg articleDetail: ArticleDetail)

}