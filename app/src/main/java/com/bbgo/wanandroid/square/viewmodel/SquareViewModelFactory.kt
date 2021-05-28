package com.bbgo.wanandroid.square.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbgo.wanandroid.square.repository.SquareRepository

/**
 *  author: wangyb
 *  date: 3/29/21 9:47 PM
 *  description: todo
 */

class SquareViewModelFactory(private val repository: SquareRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SquareViewModel(repository) as T
    }
}