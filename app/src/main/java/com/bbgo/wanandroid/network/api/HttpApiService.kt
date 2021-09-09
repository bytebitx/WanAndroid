package com.bbgo.wanandroid.network.api

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.wanandroid.bean.CollectBean
import com.bbgo.wanandroid.bean.UserInfo
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
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

    /**
     * 获取个人积分，需要登录后访问
     * https://www.wanandroid.com/lg/coin/userinfo/json
     */
    @GET("/lg/coin/userinfo/json")
    fun getUserInfo(): Flow<HttpResult<UserInfo>>


}