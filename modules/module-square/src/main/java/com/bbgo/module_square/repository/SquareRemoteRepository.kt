package com.bbgo.module_square.repository

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_square.bean.ArticleData
import com.bbgo.module_square.net.HttpSquareService
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


    fun getSquareList(pageNum: Int) : Flow<HttpResult<ArticleData>> {
        return HttpSquareService.service.getSquareList(pageNum)
    }
}