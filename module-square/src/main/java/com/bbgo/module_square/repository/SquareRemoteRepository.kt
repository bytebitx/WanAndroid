package com.bbgo.module_square.repository

import com.bbgo.module_square.bean.Articles
import com.bbgo.module_square.net.HttpSquareService
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
            emit(HttpSquareService.service.getSquareList(pageNum))
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