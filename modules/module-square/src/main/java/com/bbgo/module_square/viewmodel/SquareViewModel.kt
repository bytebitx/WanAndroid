package com.bbgo.module_square.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logE
import com.bbgo.module_square.bean.ArticleDetail
import com.bbgo.module_square.repository.SquareRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class SquareViewModel(private val repository: SquareRepository) : ViewModel() {


    val articleLiveData = MutableLiveData<Resource<List<ArticleDetail>>>()

    fun getSquareList(pageNum: Int) {
        articleLiveData.value = Resource.Loading()
        viewModelScope.launch {
            repository.getSquareList(pageNum)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    articleLiveData.value = Resource.Success(it.data.datas)
                }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}