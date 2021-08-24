package com.bbgo.module_compose.util

import com.bbgo.module_compose.repository.ComposeLocalRepository
import com.bbgo.module_compose.repository.ComposeRemoteRepository
import com.bbgo.module_compose.repository.ComposeRepository
import com.bbgo.module_compose.viewmodel.ComposeViewModelFactory


/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getComposeViewModelFactory() = ComposeViewModelFactory(
        ComposeRepository.getInstance(
            ComposeRemoteRepository.instance, ComposeLocalRepository.getInstance()))

}