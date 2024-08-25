package com.bytebitx.compose.util

import com.bytebitx.compose.repository.ComposeLocalRepository
import com.bytebitx.compose.repository.ComposeRemoteRepository
import com.bytebitx.compose.repository.ComposeRepository
import com.bytebitx.compose.viewmodel.ComposeViewModelFactory


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