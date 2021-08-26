package com.bbgo.module_square.util

import com.bbgo.module_square.repository.SquareLocalRepository
import com.bbgo.module_square.repository.SquareRemoteRepository
import com.bbgo.module_square.repository.SquareRepository
import com.bbgo.module_square.viewmodel.SquareViewModelFactory

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getSquareViewModelFactory() = SquareViewModelFactory(
        SquareRepository.getInstance(
            SquareRemoteRepository.instance, SquareLocalRepository.getInstance()))

}