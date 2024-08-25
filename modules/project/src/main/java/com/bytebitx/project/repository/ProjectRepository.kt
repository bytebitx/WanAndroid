package com.bytebitx.project.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.project.bean.*
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
@ActivityRetainedScoped
class ProjectRepository @Inject constructor(private val remoteRepository: ProjectRemoteRepository, private val localRepository: ProjectLocalRepository) {

    fun getProjectTree() : Flow<HttpResult<List<ProjectBean>>> {
        return remoteRepository.getProjectTree()
    }

    fun getProjectList(id: Int, page: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getProjectList(id, page)
    }

    fun insertProjectTree(projectBean: List<ProjectBean>) {
        localRepository.insertProjectTree(projectBean)
    }

    fun getProjectTreeFromDB() : Flow<List<ProjectBean>> {
        return localRepository.getProjectTree()
    }

    fun insertProjectArticles(articleDetail: List<ArticleDetail>) {
        localRepository.insertProjectArticles(articleDetail)
    }

    fun getProjectArticlesFromDB() : Flow<MutableList<ArticleDetail>> {
        return localRepository.getProjectArticles()
    }

    fun getTagsFromDB() : Flow<List<Tag>> {
        return localRepository.getTags()
    }

    fun getArticleDetailWithTagFromDB() : Flow<MutableList<ArticleDetailWithTag>> {
        return localRepository.getArticleDetailWithTag()
    }

    fun deleteArticleById(articleId: String) {
        localRepository.deleteArticleById(articleId)
    }

    suspend fun downloadFile(url: String, path: String) {
        localRepository.downloadFile(url, path)
    }

}