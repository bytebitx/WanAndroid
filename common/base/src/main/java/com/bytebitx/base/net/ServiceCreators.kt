package com.bytebitx.base.net

import com.bytebitx.base.BaseApplication
import com.bytebitx.base.net.interceptor.BasicParamsInterceptor
import com.bytebitx.base.net.interceptor.CacheInterceptor
import com.bytebitx.base.net.interceptor.LoggingInterceptor
import com.bytebitx.base.net.interceptor.MultiBaseUrlInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *  author: wangyb
 *  date: 3/29/21 5:46 PM
 *  description: 服务创建类
 */
object ServiceCreators {

    private const val BASE_URL = "https://wanandroid.com/"

    private const val MAX_CACHE_SIZE: Long = 1024 * 1024 * 50 // 50M 的缓存大小
    //设置 请求的缓存的大小跟位置
    private val cacheFile = File(BaseApplication.getContext().cacheDir, "httpCache")
    private val cache = Cache(cacheFile, MAX_CACHE_SIZE)
    private val cookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(BaseApplication.getContext())
    )

    val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(MultiBaseUrlInterceptor())
        .addInterceptor(LoggingInterceptor())
        .addInterceptor(CacheInterceptor())
        .cookieJar(cookieJar)
        .cache(cache)
        .addInterceptor(BasicParamsInterceptor())
        .build()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addCallAdapterFactory(FlowCallAdapterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().registerTypeAdapterFactory(
            GsonTypeAdapterFactory()
        ).create()))

    private val retrofit = builder.build()

//    val service: HttpApiService by lazy { retrofit.create(HttpApiService::class.java) }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}
