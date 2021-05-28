package com.bbgo.wanandroid.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbgo.wanandroid.wechat.repository.WxRepository

/**
 *  author: wangyb
 *  date: 3/29/21 9:47 PM
 *  description: todo
 */

class MainViewModelFactory(private val repository: WxRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}