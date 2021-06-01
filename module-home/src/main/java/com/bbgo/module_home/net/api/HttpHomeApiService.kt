package com.bbgo.module_home.net.api

import com.bbgo.module_home.bean.Articles
import com.bbgo.module_home.bean.BannerResponse
import com.bbgo.module_home.bean.TopArticles
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpHomeApiService {

    @GET("banner/json")
    suspend fun getBanners(): BannerResponse


    /**
     * 获取首页置顶文章列表
     * http://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): TopArticles

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    suspend fun getArticles(@Path("pageNum") pageNum: Int): Articles

}