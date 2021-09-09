package com.bbgo.wanandroid.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.HTTP_REQUEST_ERROR
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.USER_NOT_LOGIN
import com.bbgo.common_base.ext.logE
import com.bbgo.common_service.login.LoginOutService
import com.bbgo.wanandroid.bean.UserInfo
import com.bbgo.wanandroid.repository.UserInfoRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class MainViewModel(private val repository: UserInfoRepository) : ViewModel() {

    private val TAG = "MainViewModel"

    val userInfoLiveData = MutableLiveData<Resource<UserInfo>>()
    val logOutLiveData = MutableLiveData<Resource<String>>()

    fun getUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo().map {
                when (it.errorCode) {
                    -1 -> {
                        Resource.DataError(
                            HTTP_REQUEST_ERROR,
                            it.errorMsg
                        )
                    }
                    USER_NOT_LOGIN -> {
                        Resource.DataError(
                            USER_NOT_LOGIN,
                            it.errorMsg
                        )
                    }
                    else -> {
                        Resource.Success(it.data)
                    }
                }
            }.catch {
                logE(TAG, it.message, it)
            }.collectLatest {
                userInfoLiveData.value = it
            }
        }
    }

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