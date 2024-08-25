package com.bytebitx.sys.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytebitx.base.ext.HTTP_REQUEST_ERROR
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.util.log.Logs
import com.bytebitx.sys.bean.KnowledgeTree
import com.bytebitx.sys.bean.NaviData
import com.bytebitx.sys.repository.SysRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
@ActivityRetainedScoped
class SysViewModel @Inject constructor(private val repository: SysRepository) : ViewModel() {

    private val TAG = "SysViewModel"

    val treeLiveData = MutableLiveData<Resource<List<KnowledgeTree>>>()

    val naviLiveData = MutableLiveData<Resource<List<NaviData>>>()

    fun getKnowledgeTree() = viewModelScope.launch {
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        repository.getKnowledgeTree()
            .map {
                if (it.errorCode == HTTP_REQUEST_ERROR) {
                    Resource.Error(Exception(it.errorMsg))
                } else {
                    Resource.Success(it.data)
                }
            }
            .catch {
                Logs.e(TAG, it.message, it)
            }
            .collectLatest {
                treeLiveData.value = it
            }
    }

    fun getNavigationList() = viewModelScope.launch {
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        repository.getNavigationList()
            .map {
                if (it.errorCode == HTTP_REQUEST_ERROR) {
                    Resource.Error(Exception(it.errorMsg))
                } else {
                    Resource.Success(it.data)
                }
            }
            .catch {
                Logs.e(TAG, it.message, it)
            }
            .collectLatest {
                naviLiveData.value = it
            }
    }
}