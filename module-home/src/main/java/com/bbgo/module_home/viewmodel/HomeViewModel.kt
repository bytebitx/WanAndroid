package com.bbgo.module_home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.logE
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.bean.Banner
import com.bbgo.module_home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class HomeViewModel(private val repository: HomeRepository) : ViewModel() {


    val articleLiveData = MutableLiveData<Resource<MutableList<ArticleDetail>>>()
    val bannerLiveData = MutableLiveData<Resource<List<Banner>>>()

    fun getArticles(pageNum: Int) {
        articleLiveData.value = Resource.Loading()
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
                .flowOn(Dispatchers.IO)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    articleLiveData.value = Resource.Success(it.invoke())
                }
        }
    }

    fun getBanner() {
        viewModelScope.launch {
            repository.getBanners()
                .map {
                    repository.insertBanners(it.data)
                    it.data
                }
                .catch {
                    logD("HomeViewModel", "${it.message}")
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    bannerLiveData.value = Resource.Success(it)
                }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}