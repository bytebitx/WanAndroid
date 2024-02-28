package com.bbgo.module_compose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.util.log.Logs
import com.bbgo.module_compose.bean.ArticleDetail
import com.bbgo.module_compose.repository.ComposeRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class ComposeViewModel(private val repository: ComposeRepository) : ViewModel() {


    val articleLiveData = MutableLiveData<Resource<MutableList<ArticleDetail>>>()

    fun getArticles(pageNum: Int) {
        viewModelScope.launch {
            repository.getTopArticles()
                .zip(repository.getArticles(pageNum)) { topArticles, articles ->
                    {
                        val allArticles = mutableListOf<ArticleDetail>()
                        topArticles.data.forEach {
                            it.top = "1"
                        }
                        allArticles.addAll(topArticles.data)
                        allArticles.addAll(articles.data.datas)
                        allArticles
                    }
                }
                .catch {
                    Logs.e(TAG, it.message, it)
                }
                .collectLatest {
                    articleLiveData.value = Resource.Success(it.invoke())
                }
        }
    }

    companion object {
        private const val TAG = "ComposeViewModel"
    }
}