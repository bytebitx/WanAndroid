package com.bytebitx.collect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytebitx.base.constants.Constants.CollectType.COLLECT
import com.bytebitx.base.constants.Constants.CollectType.UNCOLLECT
import com.bytebitx.base.constants.Constants.CollectType.UNKNOWN
import com.bytebitx.base.event.MessageEvent
import com.bytebitx.base.ext.USER_NOT_LOGIN
import com.bytebitx.base.util.log.Logs
import com.bytebitx.collect.repository.CollectRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class CollectViewModel(private val repository: CollectRepository) : ViewModel() {

    fun collectArticle(indexPage: Int, position: Int, id: Int) = viewModelScope.launch {
        repository.collectArticle(id)
            .catch {
                Logs.e(it, it.message)
            }
            .collectLatest {
                val event = if (it.errorCode == USER_NOT_LOGIN) {
                    MessageEvent(indexPage, UNKNOWN, position, id)
                } else {
                    MessageEvent(indexPage, COLLECT, position, id)
                }
            }
    }

    fun unCollectArticle(indexPage: Int, position: Int, id: Int) = viewModelScope.launch {
        repository.unCollectArticle(id)
            .catch {
                Logs.e(it, it.message)
            }
            .collectLatest {
                val event = if (it.errorCode == USER_NOT_LOGIN) {
                    MessageEvent(indexPage, UNKNOWN, position, id)
                } else {
                    MessageEvent(indexPage, UNCOLLECT, position, id)
                }
            }
    }

    companion object {
        private const val TAG = "CollectViewModel"
    }
}