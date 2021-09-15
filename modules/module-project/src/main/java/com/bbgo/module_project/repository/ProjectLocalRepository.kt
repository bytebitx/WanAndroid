package com.bbgo.module_project.repository

import android.util.Log
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.net.download.DownloadListener
import com.bbgo.common_base.net.download.FileDownloader
import com.bbgo.module_project.bean.ArticleDetail
import com.bbgo.module_project.bean.ArticleDetailWithTag
import com.bbgo.module_project.bean.ProjectBean
import com.bbgo.module_project.bean.Tag
import com.bbgo.module_project.local.AppDatabase
import com.bbgo.module_project.local.DBUtil
import com.bbgo.module_project.local.dao.ArticleDetailDao
import com.bbgo.module_project.local.dao.ProjectTreeDao
import com.bbgo.module_project.local.dao.TagDao
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 *  author: wangyb
 *  date: 3/30/21 2:36 PM
 *  description: todo
 */
@ActivityRetainedScoped
class ProjectLocalRepository @Inject constructor() {

    fun insertProjectTree(projectBeans: List<ProjectBean>) {
        DBUtil.getInstance().runInTransaction {
            projectBeans.forEach { projectBean ->
                DBUtil.getInstance().projectTreeDao().insert(projectBean)
            }
        }
    }

    fun getProjectTree() : Flow<List<ProjectBean>> {
        return DBUtil.getInstance().projectTreeDao().getProjectTree()
    }

    fun insertProjectArticles(articleDetails: List<ArticleDetail>) {
        DBUtil.getInstance().runInTransaction {
            articleDetails.forEach { articleDetail ->
                DBUtil.getInstance().articleDetailDao().insert(articleDetail)
                articleDetail.tags?.forEach { tag ->
                    tag.artileId = articleDetail.id
                    insertTag(tag)
                }
            }
        }
    }

    fun getProjectArticles() : Flow<MutableList<ArticleDetail>> {
        return DBUtil.getInstance().articleDetailDao().getArticleDetailList()
    }

    private fun insertTag(tag: Tag) {
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

    suspend fun downloadFile(url: String, path: String) {
        FileDownloader.create(url)
            .setPath(path)
            .setListener(object : DownloadListener {
                override fun onStart() {
                }

                override fun onProgress(progress: Int, total: Float) {
                }

                override fun onFinish(path: String, url: String) {
                    Log.d("ProjectLocalRepository", "path = $path , url = $url , thread = ${Thread.currentThread().name}")
                    DBUtil.getInstance().articleDetailDao().updatePathByUrl(path, url)
                }

                override fun onError(msg: String?) {
                }
            })
            .start()
    }
}