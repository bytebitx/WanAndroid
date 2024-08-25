package com.bytebitx.square.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.base.net.ServiceCreators
import com.bytebitx.square.bean.ArticleData
import com.bytebitx.square.net.api.HttpSquareApiService
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/30/21 2:35 PM
 *  description: todo
 */
@ActivityRetainedScoped
class SquareRemoteRepository @Inject constructor(){

    private val service = ServiceCreators.create(HttpSquareApiService::class.java)

    fun getSquareList(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return service.getSquareList(pageNum)
    }
}