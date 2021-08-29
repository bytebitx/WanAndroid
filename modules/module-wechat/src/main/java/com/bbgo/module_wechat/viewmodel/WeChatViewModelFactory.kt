package com.bbgo.module_wechat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbgo.module_wechat.repository.WxRepository
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:47 PM
 *  description: todo
 */
class WeChatViewModelFactory @Inject constructor(val repository: WxRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeChatViewModel(repository) as T
    }
}