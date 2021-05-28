package com.bbgo.wanandroid.network.api

import com.bbgo.wanandroid.bean.*
import retrofit2.http.*

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpApiService {

    @GET("banner/json")
    suspend fun getBanners(): BannerResponse

    /**
     * 获取公众号列表
     * http://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWXChapters(): WXArticles

    /**
     * 查看某个公众号历史数据
     * http://wanandroid.com/wxarticle/list/405/1/json
     * @param id 公众号 ID
     * @param page 公众号页码
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun getWXArticles(@Path("id") id: Int,
                      @Path("page") page: Int): Articles

    /**
     * 在某个公众号中搜索历史文章
     * http://wanandroid.com/wxarticle/list/405/1/json?k=Java
     * @param id 公众号 ID
     * @param key 搜索关键字
     * @param page 公众号页码
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    suspend fun queryWXArticles(@Path("id") id: Int,
                        @Query("k") key: String,
                        @Path("page") page: Int): Articles

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

    /**
     * 获取知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): Trees

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page
     * @param cid
     */
    @GET("article/list/{page}/json")
    suspend fun getKnowledgeList(@Path("page") page: Int, @Query("cid") cid: Int): Articles

    /**
     * 导航数据
     * http://www.wanandroid.com/navi/json
     */
    @GET("navi/json")
    suspend fun getNavigationList(): NaviResponse

    /**
     * 项目数据
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    suspend fun getProjectTree(): ProjectResponse

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @POST("user/login")
    @FormUrlEncoded
    suspend fun loginWanAndroid(@Field("username") username: String,
                        @Field("password") password: String): LoginRegisterResponse

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     * @param username
     * @param password
     * @param repassword
     */
    @POST("user/register")
    @FormUrlEncoded
    suspend fun registerWanAndroid(@Field("username") username: String,
                           @Field("password") password: String,
                           @Field("repassword") repassword: String): LoginRegisterResponse

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    suspend fun logout(): BaseBean

    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id article id
     */
    @POST("lg/collect/{id}/json")
    suspend fun addCollectArticle(@Path("id") id: Int): CollectBean

    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param id
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectArticle(@Path("id") id: Int): CollectBean


    /**
     * 广场列表数据
     * https://wanandroid.com/user_article/list/0/json
     * @param pageNum 页码拼接在url上从0开始
     */
    @GET("user_article/list/{pageNum}/json")
    suspend fun getSquareList(@Path("pageNum") pageNum: Int): Articles


}