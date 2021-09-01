package com.bbgo.module_login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.USER_REGISTERED_ERROR
import com.bbgo.common_base.ext.logD
import com.bbgo.module_login.bean.LoginData
import com.bbgo.module_login.repository.RegisterLoginRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
@ActivityRetainedScoped
class RegisterLoginViewModel @Inject constructor(private val repository: RegisterLoginRepository) : ViewModel() {

    val registerLoginLiveData = MutableLiveData<Resource<LoginData>>()
    val logOutLiveData = MutableLiveData<Resource<String>>()

    fun register(username: String, password: String, repassword: String) {
        registerLoginLiveData.value = Resource.Loading()
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.registerWanAndroid(username, password, repassword)
                .map {
                    if (it.errorCode == -1) {
                        Resource.DataError(
                            USER_REGISTERED_ERROR,
                            it.errorMsg
                        )
                    } else {
                        repository.insertLoginData(it.data.username)
                        Resource.Success(it.data)
                    }
                }
                .catch {
                    logD(TAG, "catch $it")
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    registerLoginLiveData.value = it
                }
        }
    }

    fun login(username: String, password: String) {
        registerLoginLiveData.value = Resource.Loading()
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.loginWanAndroid(username, password)
                .map {
                    logD(TAG, "map current thread ${Thread.currentThread().name}")
                    if (it.errorCode == -1) {
                        Resource.DataError(
                            USER_REGISTERED_ERROR,
                            it.errorMsg
                        )
                    } else {
                        repository.insertLoginData(it.data.username)
                        Resource.Success(it.data)
                    }
                }
                .catch {
                    logD(TAG, "catch $it")
                }
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    logD("HomeViewModel", "collect current thread ${Thread.currentThread().name}")
                    registerLoginLiveData.value = it
                }
        }
    }

    fun logOut() {
        logOutLiveData.value = Resource.Loading()
        viewModelScope.launch {
            /**
             * 1.必须要有异常处理
             * 2.必须要有collect，否则map里面的代码不执行
             */
            repository.logout()
                .map {
                    if (it.errorCode == 0) {
                        repository.insertLoginData("")
                    }
                    it
                }
                .catch {
                }
                .collectLatest {
                    if (it.errorCode != 0) {
                        logOutLiveData.value = Resource.DataError(it.errorCode, it.errorMsg)
                        return@collectLatest
                    }
                    logOutLiveData.value = Resource.Success("")
                }
        }
    }

    suspend fun logOutToMain(): Flow<String> {
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        return repository.logout()
            .map {
                val str = if (it.errorCode == 0) {
                    repository.insertLoginData("")
                    ""
                } else {
                    it.errorMsg
                }
                str
            }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}