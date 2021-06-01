package com.bbgo.wanandroid.collect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import org.greenrobot.eventbus.EventBus

/**
 *  author: wangyb
 *  date: 3/29/21 9:31 PM
 *  description: todo
 */
class CollectViewModel(private val repository: CollectRepository) : ViewModel() {

    fun collectArticle(position: Int, id: Int) {
        viewModelScope.launch {
            repository.collectArticle(id)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    if (it.errorCode == USER_NOT_LOGIN) {
                        EventBus.getDefault().post(MessageEvent(UNKNOWN, position, id))
                    } else {
                        it.positon = position
                        it.type = COLLECT
                        EventBus.getDefault().post(MessageEvent(COLLECT, position, id))
                    }
                }
        }
    }

    fun unCollectArticle(position: Int, id: Int) {
        viewModelScope.launch {
            repository.unCollectArticle(id)
                .catch {
                    logE(TAG, it.message, it)
                }
                .collectLatest {
                    if (it.errorCode == USER_NOT_LOGIN) {
                        EventBus.getDefault().post(MessageEvent(UNKNOWN, position, id))
                    } else {
                        it.positon = position
                        it.type = UNCOLLECT
                        EventBus.getDefault().post(MessageEvent(UNCOLLECT, position, id))
                    }
                }
        }
    }

    companion object {
        private const val TAG = "CollectViewModel"
    }
}