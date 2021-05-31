package com.bbgo.module_login.net.api

import com.bbgo.common_base.bean.BaseBean
import com.bbgo.module_login.bean.LoginRegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *  author: wangyb
 *  date: 4/7/21 9:24 PM
 *  description: http api
 */
interface HttpLoginApiService {



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

}