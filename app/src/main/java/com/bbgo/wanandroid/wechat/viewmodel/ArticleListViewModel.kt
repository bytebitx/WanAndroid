package com.bbgo.wanandroid.wechat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.HTTP_REQUEST_ERROR
import com.bbgo.common_base.ext.Resource
import com.bbgo.wanandroid.bean.ArticleDetail
import com.bbgo.wanandroid.wechat.repository.ArticleListRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class ArticleListViewModel(private val repository: ArticleListRepository) : ViewModel() {

    val articleListLiveData by lazy { MutableLiveData<Resource<MutableList<ArticleDetail>>>() }

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
                .collectLatest {
                    articleListLiveData.value = it
                }
        }
    }
}