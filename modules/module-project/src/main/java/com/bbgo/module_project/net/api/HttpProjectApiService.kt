package com.bbgo.module_project.net.api

import com.bbgo.common_base.bean.HttpResult
import com.bbgo.module_project.bean.ArticleData
import com.bbgo.module_project.bean.ProjectBean
import kotlinx.coroutines.flow.Flow
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
    fun getProjectTree(): Flow<HttpResult<List<ProjectBean>>>

    /**
     * 项目列表数据
     * http://www.wanandroid.com/project/list/1/json?cid=294
     * @param page
     * @param cid
     */
    @GET("project/list/{page}/json")
    fun getProjectList(@Path("page") page: Int, @Query("cid") cid: Int): Flow<HttpResult<ArticleData>>

}