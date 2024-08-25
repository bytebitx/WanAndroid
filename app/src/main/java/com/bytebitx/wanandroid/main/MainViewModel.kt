package com.bytebitx.wanandroid.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytebitx.base.ext.Resource
import com.bytebitx.service.login.LoginOutService
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class MainViewModel : ViewModel() {

    val logOutLiveData = MutableLiveData<Resource<String>>()

    fun logOut(loginOutService: LoginOutService) {
        viewModelScope.launch {
            loginOutService.logOut()
                .catch {

                }
                .collectLatest {
                    logOutLiveData.value = Resource.Success(it)
                }
        }
    }

}