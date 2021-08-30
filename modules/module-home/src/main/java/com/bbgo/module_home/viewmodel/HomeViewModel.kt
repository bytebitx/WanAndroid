package com.bbgo.module_home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.logE
import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.repository.HomeRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
@ActivityRetainedScoped
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

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
                .catch {
                    logD("HomeViewModel", "${it.message}")
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    bannerLiveData.value = Resource.Success(it.data)
                }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}