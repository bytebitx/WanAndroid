package com.bbgo.wanandroid.wechat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.HTTP_REQUEST_ERROR
import com.bbgo.common_base.ext.Resource
import com.bbgo.wanandroid.bean.ArticleDetail
import com.bbgo.wanandroid.bean.WXArticle
import com.bbgo.wanandroid.wechat.repository.WxRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class WeChatViewModel(private val repository: WxRepository) : ViewModel() {

    val wxChapterLiveData = MutableLiveData<Resource<List<WXArticle>>>()
    val wxArticlesLiveData = MutableLiveData<Resource<MutableList<ArticleDetail>>>()

    fun getWXChapters() {
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.getWXChapters()
                .map {
                    if (it.errorCode == HTTP_REQUEST_ERROR) {
                        Resource.DataError(it.errorCode, it.errorMsg)
                    } else {
                        repository.insertWXArticles(it.data)
                        Resource.Success(it.data)
                    }
                }
                .catch {
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    wxChapterLiveData.value = it
                }
        }
    }

    fun getWXArticles(id: Int, page: Int) {
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.getWXArticles(id, page)
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
}