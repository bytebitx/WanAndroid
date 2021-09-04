//package com.bbgo.common_base.di
//
//import com.bbgo.common_base.BaseApplication
//import com.bbgo.common_base.constants.HttpConstant
//import com.bbgo.common_base.net.GsonTypeAdapterFactory
//import com.bbgo.common_base.net.ServiceCreators
//import com.bbgo.common_base.net.interceptor.*
//import com.google.gson.GsonBuilder
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.Cache
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.io.File
//import javax.inject.Singleton
//
///**
// * @Description:
// * @Author: wangyuebin
// * @Date: 2021/8/30 5:05 下午
// */
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetWorkModule {
//
//    private val BASE_URL = "https://wanandroid.com/"
//
//    //设置 请求的缓存的大小跟位置
//    private val cacheFile = File(BaseApplication.getContext().cacheDir, "cache")
//    private val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(MultiBaseUrlInterceptor())
//            .addInterceptor(LoggingInterceptor())
//            .addInterceptor(HeaderInterceptor())
//            .addInterceptor(SaveCookieInterceptor())
//            .addInterceptor(CacheInterceptor())
//            .cache(cache)
//            .addInterceptor(BasicParamsInterceptor())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(ServiceCreators.httpClient)
//            .addConverterFactory(
//                GsonConverterFactory.create(
//                    GsonBuilder().registerTypeAdapterFactory(
//                GsonTypeAdapterFactory()
//            ).create())).build()
//    }
//}