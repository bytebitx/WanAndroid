package com.bbgo.module_sys.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.HTTP_REQUEST_ERROR
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.logE
import com.bbgo.module_sys.bean.ArticleDetail
import com.bbgo.module_sys.bean.KnowledgeTree
import com.bbgo.module_sys.bean.NaviData
import com.bbgo.module_sys.repository.SysRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class SysViewModel(private val repository: SysRepository) : ViewModel() {

    private val TAG = "SysViewModel"

    val wxArticlesLiveData by lazy { MutableLiveData<Resource<MutableList<ArticleDetail>>>() }

    val treeLiveData by lazy { MutableLiveData<Resource<List<KnowledgeTree>>>() }

    val naviLiveData by lazy { MutableLiveData<Resource<List<NaviData>>>() }

    fun getKnowledgeTree() {
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.getKnowledgeTree()
                .map {
                    if (it.errorCode == HTTP_REQUEST_ERROR) {
                        Resource.DataError(it.errorCode, it.errorMsg)
                    } else {
                        Resource.Success(it.data)
                    }
                }
                .catch {
                    logE(TAG, it.message, it)
                }
                .collect {
                    treeLiveData.value = it
                }
        }
    }

    fun getKnowledgeList(id: Int, page: Int) {
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.getKnowledgeList(id, page)
                .map {
                    if (it.errorCode == HTTP_REQUEST_ERROR) {
                        Resource.DataError(it.errorCode, it.errorMsg)
                    } else {
                        Resource.Success(it.data.datas)
                    }
                }
                .catch {
                }
                .collect {
                    wxArticlesLiveData.value = it
                }
        }
    }

    fun getNavigationList() {
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.getNavigationList()
                .map {
                    if (it.errorCode == HTTP_REQUEST_ERROR) {
                        Resource.DataError(it.errorCode, it.errorMsg)
                    } else {
                        Resource.Success(it.data)
                    }
                }
                .catch {
                    logE(TAG, it.message, it)
                }
                .collect {
                    naviLiveData.value = it
                }
        }
    }
}