package com.bytebitx.collect.net.api

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.collect.bean.CollectBean
import kotlinx.coroutines.flow.Flow
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpApiService {

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id article id
     */
    @POST("lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id: Int): Flow<HttpResult<CollectBean>>

    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param id
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Flow<HttpResult<CollectBean>>


}