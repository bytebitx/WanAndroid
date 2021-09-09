package com.bbgo.module_wechat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.HTTP_REQUEST_ERROR
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logE
import com.bbgo.module_wechat.bean.ArticleDetail
import com.bbgo.module_wechat.bean.WXArticle
import com.bbgo.module_wechat.repository.WxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
                        Resource.Success(it.data)
                    }
                }
                .catch {
                }
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
                    logE(TAG, it.message, it)
                }
                .collectLatest {
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
                .collectLatest {
                    wxArticlesLiveData.value = it
                }
        }
    }

    private val TAG = "WeChatViewModel"
}