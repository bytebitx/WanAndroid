package com.bbgo.wanandroid.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.HTTP_REQUEST_ERROR
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.USER_REGISTERED_ERROR
import com.bbgo.common_base.ext.logD
import com.bbgo.module_login.bean.LoginData
import com.bbgo.module_login.repository.RegisterLoginRepository
import com.bbgo.wanandroid.bean.CoinInfoBean
import com.bbgo.wanandroid.bean.UserInfo
import com.bbgo.wanandroid.bean.UserInfoBean
import com.bbgo.wanandroid.bean.UserScoreBean
import com.bbgo.wanandroid.network.HttpService
import com.bbgo.wanandroid.repository.UserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class MainViewModel(private val repository: UserInfoRepository) : ViewModel() {

    val userInfoLiveData = MutableLiveData<Resource<UserInfo>>()
    val userScoreLiveData = MutableLiveData<Resource<UserScoreBean>>()
    val coinInfoLiveData = MutableLiveData<Resource<CoinInfoBean>>()

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

    fun getUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo().map {
                if (it.errorCode == -1) {
                    Resource.DataError(
                        HTTP_REQUEST_ERROR,
                        it.errorMsg
                    )
                } else {
                    Resource.Success(it.data)
                }
            }.catch {

            }.collectLatest {
                userInfoLiveData.value = it
            }
        }
    }

}