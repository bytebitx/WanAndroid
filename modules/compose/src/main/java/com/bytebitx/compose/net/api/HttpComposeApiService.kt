package com.bytebitx.compose.net.api

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.compose.bean.ArticleData
import com.bytebitx.compose.bean.ArticleDetail
import kotlinx.coroutines.flow.Flow
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
    fun getTopArticles(): Flow<HttpResult<List<ArticleDetail>>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Flow<HttpResult<ArticleData>>

}