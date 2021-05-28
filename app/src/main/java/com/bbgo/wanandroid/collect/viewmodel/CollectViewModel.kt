package com.bbgo.wanandroid.collect.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.USER_NOT_LOGIN
import com.bbgo.common_base.ext.logE
import com.bbgo.wanandroid.bean.CollectBean
import com.bbgo.wanandroid.collect.repository.CollectRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class CollectViewModel(private val repository: CollectRepository) : ViewModel() {


    val collectLiveData = MutableLiveData<Resource<CollectBean>>()

    fun collectArticle(position: Int, id: Int) {
        viewModelScope.launch {
            repository.collectArticle(id)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    if (it.errorCode == USER_NOT_LOGIN) {
                        collectLiveData.value = Resource.DataError(it.errorCode, it.errorMsg)
                    } else {
                        it.positon = position
                        it.type = COLLECT
                        collectLiveData.value = Resource.Success(it)
                    }
                }
        }
    }

    fun unCollectArticle(position: Int, id: Int) {
        viewModelScope.launch {
            repository.unCollectArticle(id)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    it.positon = position
                    it.type = UNCOLLECT
                    collectLiveData.value = Resource.Success(it)
                }
        }
    }

    companion object {
        private const val TAG = "CollectViewModel"
        const val COLLECT = "COLLECT"
        const val UNCOLLECT = "UNCOLLECT"
    }
}