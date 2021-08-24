package com.bbgo.wanandroid.collect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbgo.common_base.bus.BusKey
import com.bbgo.common_base.bus.LiveDataBus
import com.bbgo.common_base.constants.Constants.CollectType.COLLECT
import com.bbgo.common_base.constants.Constants.CollectType.UNCOLLECT
import com.bbgo.common_base.constants.Constants.CollectType.UNKNOWN
import com.bbgo.common_base.event.MessageEvent
import com.bbgo.common_base.ext.USER_NOT_LOGIN
import com.bbgo.common_base.ext.logE
import com.bbgo.wanandroid.collect.repository.CollectRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class CollectViewModel(private val repository: CollectRepository) : ViewModel() {

    fun collectArticle(indexPage: Int, position: Int, id: Int) {
        viewModelScope.launch {
            repository.collectArticle(id)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    val event = if (it.errorCode == USER_NOT_LOGIN) {
                        MessageEvent(indexPage, UNKNOWN, position, id)
                    } else {
                        it.data.positon = position
                        it.data.type = COLLECT
                        MessageEvent(indexPage, COLLECT, position, id)
                    }
                    LiveDataBus.get().with(BusKey.COLLECT).value = event
                }
        }
    }

    fun unCollectArticle(indexPage: Int, position: Int, id: Int) {
        viewModelScope.launch {
            repository.unCollectArticle(id)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    val event = if (it.errorCode == USER_NOT_LOGIN) {
                        MessageEvent(indexPage, UNKNOWN, position, id)
                    } else {
                        it.data.positon = position
                        it.data.type = UNCOLLECT
                        MessageEvent(indexPage, UNCOLLECT, position, id)
                    }
                    LiveDataBus.get().with(BusKey.COLLECT).value = event
                }
        }
    }

    companion object {
        private const val TAG = "CollectViewModel"
    }
}