package com.bytebitx.square.net.api

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.square.bean.ArticleData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpSquareApiService {

    /**
     * 广场列表数据
     * https://wanandroid.com/user_article/list/0/json
     * @param pageNum 页码拼接在url上从0开始
     */
    @GET("user_article/list/{pageNum}/json")
    fun getSquareList(@Path("pageNum") pageNum: Int): Flow<HttpResult<ArticleData>>

}