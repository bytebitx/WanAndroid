package com.bbgo.module_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.util.log.Logs
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.bean.Banner
import com.bbgo.module_home.repository.HomeRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
@ActivityRetainedScoped
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val articleUiState = MutableStateFlow<Resource<List<ArticleDetail>>>(Resource.Loading())
    val bannerUiState = MutableStateFlow<Resource<List<Banner>>>(Resource.Loading())


    fun getArticles(pageNum: Int) = viewModelScope.launch {
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
                Logs.e(it, it.message)
            }
            .collectLatest {
                articleUiState.value = Resource.Success(it.invoke())
            }
    }

    fun getBanner() = viewModelScope.launch(Dispatchers.IO){
        repository.getBanners()
            .map {
                repository.insertBanners(it.data)
                it
            }
            .catch {
                Logs.e("${it.message}")
                bannerUiState.value = Resource.Error(it)
            }
            .collectLatest {
                bannerUiState.value = Resource.Success(it.data)
            }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}