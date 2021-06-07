package com.bbgo.module_project.net.api

import com.bbgo.module_project.bean.Articles
import com.bbgo.module_project.bean.ProjectTree
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpProjectApiService {

    /**
     * 项目数据
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    suspend fun getProjectTree(): ProjectTree

    /**
     * 项目列表数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     * @param page
     * @param cid
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): Articles

}