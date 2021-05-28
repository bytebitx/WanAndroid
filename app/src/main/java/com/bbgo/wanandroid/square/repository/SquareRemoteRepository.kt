package com.bbgo.wanandroid.square.repository

import com.bbgo.wanandroid.bean.Articles
import com.bbgo.wanandroid.network.HttpService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
class SquareRemoteRepository private constructor(){


    suspend fun getSquareList(pageNum: Int) : Flow<Articles> {
        return flow {
            emit(HttpService.service.getSquareList(pageNum))
        }.flowOn(Dispatchers.IO)
    }

    /********************静态内部类单例***************************/
    companion object {
        val instance = SingleTonHolder.holder
    }

    private object SingleTonHolder {
        val holder = SquareRemoteRepository()
    }
}