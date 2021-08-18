package com.bbgo.common_base.net

import com.bbgo.common_base.BaseApplication
import com.bbgo.common_base.constants.HttpConstant
import com.bbgo.common_base.net.interceptor.*
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 *  author: wangyb
 *  date: 3/29/21 5:46 PM
 *  description: 服务创建类
 */
object ServiceCreators {

    private const val BASE_URL = "https://wanandroid.com/"

    //设置 请求的缓存的大小跟位置
    private val cacheFile = File(BaseApplication.getContext().cacheDir, "cache")
    private val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

    val httpClient = OkHttpClient.Builder()
        .addInterceptor(MultiBaseUrlInterceptor())
        .addInterceptor(LoggingInterceptor())
        .addInterceptor(HeaderInterceptor())
        .addInterceptor(SaveCookieInterceptor())
        .addInterceptor(CacheInterceptor())
        .cache(cache)
        .addInterceptor(BasicParamsInterceptor())
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapterFactory(
            GsonTypeAdapterFactory()
        ).create()))

    private val retrofit = builder.build()

//    val service: HttpApiService by lazy { retrofit.create(HttpApiService::class.java) }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}
