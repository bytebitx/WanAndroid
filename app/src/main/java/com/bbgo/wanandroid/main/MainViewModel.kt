package com.bbgo.wanandroid.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.logD
import com.bbgo.wanandroid.wechat.repository.WxRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class MainViewModel(private val repository: WxRepository) : ViewModel() {

    fun getTest() {
        viewModelScope.launch {
            test()
        }
    }

    private suspend fun test() {
        flow {
            for (index in 0..30) {
                emit(index)
                delay(1000)
            }
        }.flowOn(Dispatchers.IO).collect {
            logD("MainViewModel", "MainViewModel = ${Thread.currentThread().name} ${30 - it}")
        }
    }

}