package com.bytebitx.wechat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytebitx.base.ext.HTTP_REQUEST_ERROR
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.util.log.Logs
import com.bytebitx.wechat.bean.ArticleDetail
import com.bytebitx.wechat.bean.WXArticle
import com.bytebitx.wechat.repository.WxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
@HiltViewModel
class WeChatViewModel @Inject constructor(private val repository: WxRepository) : ViewModel() {

    val wxChapterUiState = MutableStateFlow<Resource<List<WXArticle>>>(Resource.Loading())

    val wxArticlesUiState = MutableStateFlow<Resource<MutableList<ArticleDetail>>>(Resource.Loading())

    fun getWXChapters() = viewModelScope.launch {
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        repository.getWXChapters()
            .map {
                if (it.errorCode == HTTP_REQUEST_ERROR) {
                    Resource.Error(Exception(it.errorMsg))
                } else {
                    Resource.Success(it.data)
                }
            }
            .catch {
            }
            .collectLatest {
                wxChapterUiState.value = it
            }
    }

    fun getWXArticles(id: Int, page: Int) = viewModelScope.launch {
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        repository.getWXArticles(id, page)
            .map {
                if (it.errorCode == HTTP_REQUEST_ERROR) {
                    Resource.Error(Exception(it.errorMsg))
                } else {
                    Resource.Success(it.data.datas)
                }
            }
            .catch {
                Logs.e(it, it.message)
            }
            .collectLatest {
                wxArticlesUiState.value = it
            }
    }

    private val TAG = "WeChatViewModel"
}