package com.bbgo.module_compose.util

import com.bbgo.module_compose.repository.HomeLocalRepository
import com.bbgo.module_compose.repository.HomeRemoteRepository
import com.bbgo.module_compose.repository.HomeRepository
import com.bbgo.module_compose.viewmodel.ComposeViewModelFactory


/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getComposeViewModelFactory() = ComposeViewModelFactory(
        HomeRepository.getInstance(
            HomeRemoteRepository.instance, HomeLocalRepository.getInstance()))

}