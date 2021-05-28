package com.bbgo.wanandroid.wechat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbgo.wanandroid.wechat.repository.ArticleListRepository
import com.bbgo.wanandroid.wechat.repository.WxRepository

/**
 *  author: wangyb
 *  date: 3/29/21 9:47 PM
 *  description: todo
 */

class ArticleListViewModelFactory(private val repository: ArticleListRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleListViewModel(repository) as T
    }
}