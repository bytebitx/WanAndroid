package com.bytebitx.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.util.log.Logs
import com.bytebitx.login.bean.LoginData
import com.bytebitx.login.repository.RegisterLoginRepository
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

    fun register(username: String, password: String, repassword: String) = viewModelScope.launch {
        registerLoginLiveData.value = Resource.Loading()
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        repository.registerWanAndroid(username, password, repassword)
            .map {
                if (it.errorCode == -1) {
                    Resource.Error(Exception(it.errorMsg))
                } else {
                    repository.insertLoginData(it.data)
                    Resource.Success(it.data)
                }
            }
            .catch {
                Logs.e(it, "catch $it")
            }
            .collectLatest {
                registerLoginLiveData.value = it
            }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        registerLoginLiveData.value = Resource.Loading()
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        repository.loginWanAndroid(username, password)
            .map {
                if (it.errorCode == -1) {
                    Resource.Error(Exception(it.errorMsg))
                } else {
                    repository.insertLoginData(it.data)
                    Resource.Success(it.data)
                }
            }
            .flowOn(Dispatchers.IO)
            .catch {
                Logs.e(it, "catch ${it.stackTrace}")
                registerLoginLiveData.value = Resource.Error(Exception(it.message))
            }
            .collectLatest {
                registerLoginLiveData.value = it
            }
    }

    fun logOut() = viewModelScope.launch {
        logOutLiveData.value = Resource.Loading()
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        repository.logout()
            .map {
                if (it.errorCode == 0) {
                    repository.insertLoginData(null)
                }
                it
            }
            .catch {
            }
            .collectLatest {
                if (it.errorCode != 0) {
                    logOutLiveData.value = Resource.Error(Exception(it.errorMsg))
                    return@collectLatest
                }
                logOutLiveData.value = Resource.Success("")
            }
    }

    fun logOutToMain(): Flow<String> {
        /**
         * 1.必须要有异常处理
         * 2.必须要有collect，否则map里面的代码不执行
         */
        return repository.logout()
            .map {
                val str = if (it.errorCode == 0) {
                    repository.insertLoginData(null)
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