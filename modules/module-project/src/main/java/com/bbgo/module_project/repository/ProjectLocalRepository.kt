package com.bbgo.module_project.repository

import com.bbgo.module_project.bean.ArticleDetail
import com.bbgo.module_project.bean.ArticleDetailWithTag
import com.bbgo.module_project.bean.ProjectBean
import com.bbgo.module_project.bean.Tag
import com.bbgo.module_project.local.DBUtil
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
@ActivityRetainedScoped
class ProjectLocalRepository @Inject constructor(){


    fun insertProjectTree(projectBean: ProjectBean) {
        DBUtil.getInstance().projectTreeDao().insert(projectBean)
    }

    fun getProjectTree() : Flow<List<ProjectBean>> {
        return DBUtil.getInstance().projectTreeDao().getProjectTree()
    }

    fun insertProjectArticle(articleDetail: ArticleDetail) {
        DBUtil.getInstance().articleDetailDao().insert(articleDetail)
    }

    fun getProjectArticles() : Flow<MutableList<ArticleDetail>> {
        return DBUtil.getInstance().articleDetailDao().getArticleDetailList()
    }

    fun insertTag(tag: Tag) {
        DBUtil.getInstance().tagDao().insert(tag)
    }

    fun getTags() : Flow<List<Tag>> {
        return DBUtil.getInstance().tagDao().getTagList()
    }

    fun getArticleDetailWithTag() : Flow<MutableList<ArticleDetailWithTag>> {
        return DBUtil.getInstance().articleDetailDao().getArticleDetailWithTag()
    }

    fun deleteArticleById(articleId: String) {
        DBUtil.getInstance().articleDetailDao().deleteArticleById(articleId)
    }
}