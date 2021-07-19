package com.bbgo.wanandroid.util

import com.bbgo.wanandroid.collect.repository.CollectRepository
import com.bbgo.wanandroid.collect.viewmodel.CollectViewModelFactory
import com.bbgo.wanandroid.main.MainViewModelFactory
import com.bbgo.wanandroid.repository.UserInfoRepository

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getHomeViewModelFactory() = MainViewModelFactory(UserInfoRepository.getInstance())

    fun getCollectViewModelFactory() = CollectViewModelFactory(CollectRepository.getInstance())

}