package com.bbgo.wanandroid.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbgo.wanandroid.home.repository.HomeRepository
import com.bbgo.wanandroid.square.repository.SquareRepository
import com.bbgo.wanandroid.square.viewmodel.SquareViewModel

/**
 *  author: wangyb
 *  date: 3/29/21 9:47 PM
 *  description: todo
 */

class HomeViewModelFactory(private val repository: HomeRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}