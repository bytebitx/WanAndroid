package com.bbgo.module_project.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.HTTP_REQUEST_ERROR
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logE
import com.bbgo.module_project.bean.ArticleDetail
import com.bbgo.module_project.bean.ProjectBean
import com.bbgo.module_project.repository.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    val projectTreeLiveData by lazy { MutableLiveData<Resource<List<ProjectBean>>>() }
    val articlesLiveData by lazy { MutableLiveData<Resource<MutableList<ArticleDetail>>>() }

    fun getProjectTree() {
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.getProjectTree()
                .map {
                    if (it.errorCode == HTTP_REQUEST_ERROR) {
                        Resource.DataError(it.errorCode, it.errorMsg)
                    } else {
                        Resource.Success(it.data)
                    }
                }
                .catch {
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    projectTreeLiveData.value = it
                }
        }
    }

    fun getProjectList(id: Int, page: Int) {
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.getProjectList(id, page)
                .map {
                    if (it.errorCode == HTTP_REQUEST_ERROR) {
                        Resource.DataError(it.errorCode, it.errorMsg)
                    } else {
                        Resource.Success(it.data.datas)
                    }
                }
                .catch {
                    logE(TAG, it.message, it)
                }
                .collect {
                    articlesLiveData.value = it
                }
        }
    }

    private val TAG = "ProjectViewModel"
}