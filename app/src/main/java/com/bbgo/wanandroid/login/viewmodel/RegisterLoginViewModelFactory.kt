package com.bbgo.wanandroid.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbgo.wanandroid.login.repository.RegisterLoginRepository

/**
 *  author: wangyb
 *  date: 3/29/21 9:47 PM
 *  description: todo
 */

class RegisterLoginViewModelFactory(private val repository: RegisterLoginRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterLoginViewModel(repository) as T
    }
}