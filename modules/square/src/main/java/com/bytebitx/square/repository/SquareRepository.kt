package com.bytebitx.square.repository

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.square.bean.ArticleData
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 3/29/21 9:32 PM
 *  description: todo
 */
@ActivityRetainedScoped
class SquareRepository @Inject constructor(private val remoteRepository: SquareRemoteRepository, private val localRepository: SquareLocalRepository) {

    fun getSquareList(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return remoteRepository.getSquareList(pageNum)
    }

}