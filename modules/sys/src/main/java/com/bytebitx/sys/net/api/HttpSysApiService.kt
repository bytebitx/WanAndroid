package com.bytebitx.sys.net.api

import com.bytebitx.base.bean.HttpResult
import com.bytebitx.sys.bean.ArticleData
import com.bytebitx.sys.bean.KnowledgeTree
import com.bytebitx.sys.bean.NaviData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpSysApiService {

    /**
     * 获取知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    fun getKnowledgeTree(): Flow<HttpResult<List<KnowledgeTree>>>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page
     * @param cid
     */
    @GET("article/list/{page}/json")
    fun getKnowledgeList(@Path("page") page: Int, @Query("cid") cid: Int): Flow<HttpResult<ArticleData>>

    /**
     * 导航数据
     * http://www.wanandroid.com/navi/json
     */
    @GET("navi/json")
    fun getNavigationList(): Flow<HttpResult<List<NaviData>>>


}