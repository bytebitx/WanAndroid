package com.bytebitx.wechat.net.api

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.wechat.bean.ArticleData
import com.bytebitx.wechat.bean.WXArticle
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpWeChatApiService {

    /**
     * 获取公众号列表
     * http://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    fun getWXChapters(): Flow<HttpResult<List<WXArticle>>>

    /**
     * 查看某个公众号历史数据
     * http://wanandroid.com/wxarticle/list/405/1/json
     * @param id 公众号 ID
     * @param page 公众号页码
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getWXArticles(@Path("id") id: Int,
                              @Path("page") page: Int): Flow<HttpResult<ArticleData>>

    /**
     * 在某个公众号中搜索历史文章
     * http://wanandroid.com/wxarticle/list/405/1/json?k=Java
     * @param id 公众号 ID
     * @param key 搜索关键字
     * @param page 公众号页码
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun queryWXArticles(@Path("id") id: Int,
                                @Query("k") key: String,
                                @Path("page") page: Int): Flow<HttpResult<ArticleData>>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page
     * @param cid
     */
    @GET("article/list/{page}/json")
    fun getKnowledgeList(@Path("page") page: Int, @Query("cid") cid: Int): Flow<HttpResult<ArticleData>>

}