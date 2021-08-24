package com.bbgo.module_compose.net.api

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_compose.bean.ArticleData
import com.bbgo.module_compose.bean.ArticleDetail
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpComposeApiService {

    /**
     * 获取首页置顶文章列表
     * http://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): HttpResult<List<ArticleDetail>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    suspend fun getArticles(@Path("pageNum") pageNum: Int): HttpResult<ArticleData>

}