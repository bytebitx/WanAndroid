package com.bbgo.wanandroid.util

import com.bbgo.module_home.repository.HomeLocalRepository
import com.bbgo.module_home.repository.HomeRemoteRepository
import com.bbgo.module_home.repository.HomeRepository
import com.bbgo.module_home.viewmodel.HomeViewModelFactory
import com.bbgo.wanandroid.collect.repository.CollectRepository
import com.bbgo.wanandroid.collect.viewmodel.CollectViewModelFactory

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getHomeViewModelFactory() = HomeViewModelFactory(
        HomeRepository.getInstance(
        HomeRemoteRepository.instance, HomeLocalRepository.getInstance()))

    fun getCollectViewModelFactory() = CollectViewModelFactory(CollectRepository.getInstance())

}