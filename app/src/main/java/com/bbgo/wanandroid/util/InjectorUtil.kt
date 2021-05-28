package com.bbgo.wanandroid.util

import com.bbgo.wanandroid.collect.repository.CollectRepository
import com.bbgo.wanandroid.home.repository.HomeLocalRepository
import com.bbgo.wanandroid.home.repository.HomeRemoteRepository
import com.bbgo.wanandroid.home.repository.HomeRepository
import com.bbgo.wanandroid.collect.viewmodel.CollectViewModelFactory
import com.bbgo.wanandroid.home.viewmodel.HomeViewModelFactory
import com.bbgo.wanandroid.local.DBUtil
import com.bbgo.wanandroid.login.repository.RegisterLoginLocalRepository
import com.bbgo.wanandroid.login.repository.RegisterLoginRemoteRepository
import com.bbgo.wanandroid.login.repository.RegisterLoginRepository
import com.bbgo.wanandroid.login.viewmodel.RegisterLoginViewModelFactory
import com.bbgo.wanandroid.square.repository.SquareLocalRepository
import com.bbgo.wanandroid.square.repository.SquareRemoteRepository
import com.bbgo.wanandroid.square.repository.SquareRepository
import com.bbgo.wanandroid.square.viewmodel.SquareViewModelFactory
import com.bbgo.wanandroid.wechat.repository.*
import com.bbgo.wanandroid.wechat.viewmodel.ArticleListViewModelFactory
import com.bbgo.wanandroid.wechat.viewmodel.WeChatViewModelFactory

/**
 *  author: wangyb
 *  date: 3/29/21 9:44 PM
 *  description: todo
 */
object InjectorUtil {

    fun getHomeViewModelFactory() = HomeViewModelFactory(
        HomeRepository.getInstance(
        HomeRemoteRepository.instance, HomeLocalRepository.getInstance(DBUtil.getInstance().WXArticleDao())))

    fun getLoginViewModelFactory() = RegisterLoginViewModelFactory(RegisterLoginRepository.getInstance(
        RegisterLoginRemoteRepository.instance, RegisterLoginLocalRepository.getInstance(DBUtil.getInstance().WXArticleDao())))

    fun getCollectViewModelFactory() = CollectViewModelFactory(CollectRepository.getInstance())

    fun getWeChatViewModelFactory() = WeChatViewModelFactory(
        WxRepository.getInstance(WxRemoteRepository.instance, WxLocalRepository.getInstance(DBUtil.getInstance().WXArticleDao())))

    fun getArticleListViewModelFactory() = ArticleListViewModelFactory(
        ArticleListRepository.getInstance(
            ArticleListRemoteRepository.instance, ArticleListLocalRepository.getInstance(DBUtil.getInstance().WXArticleDao()))
    )

    fun getSquareViewModelFactory() = SquareViewModelFactory(
        SquareRepository.getInstance(
            SquareRemoteRepository.instance, SquareLocalRepository.getInstance(DBUtil.getInstance().WXArticleDao())))
}