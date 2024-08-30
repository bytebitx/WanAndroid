package com.bytebitx.square.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.util.log.Logs
import com.bytebitx.square.bean.ArticleDetail
import com.bytebitx.square.repository.SquareRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
@ActivityRetainedScoped
class SquareViewModel @Inject constructor(private val repository: SquareRepository) : ViewModel() {


    val articleLiveData = MutableLiveData<Resource<List<ArticleDetail>>>()

    fun getSquareList(pageNum: Int) = viewModelScope.launch {
        articleLiveData.value = Resource.Loading()
        repository.getSquareList(pageNum)
            .catch {
                Logs.e(it, it.message)
            }
            .collectLatest {
                articleLiveData.value = Resource.Success(it.data.datas)
            }
    }

    companion object {
        private const val TAG = "SquareViewModel"
    }
}