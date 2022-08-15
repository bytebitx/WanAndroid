package com.bbgo.common_base.net.interceptor

import android.text.TextUtils
import com.bbgo.common_base.util.log.Logs
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class MultiBaseUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取原始的request
        val originalRequest = chain.request()
        //获取原始的url
        val originalUrl = originalRequest.url
        //获取原始的request的创建者builder
        val builder = originalRequest.newBuilder()
        //获取原始的headers
        val headers = originalRequest.headers
        val baseUrl = headers["baseUrl"]//获取请求头里面设置的baseurl
        Logs.d("originalUrl=$originalUrl")
        Logs.d("baseUrl=$baseUrl")
        return if (!TextUtils.isEmpty(baseUrl)) {
            val baseHttpUrl = baseUrl?.toHttpUrlOrNull()//将其解析成httpurl
            //重建新的HttpUrl，需要重新设置的url部分
            val newHttpUrl = originalUrl.newBuilder()
                .scheme(baseHttpUrl?.scheme!!)
                .host(baseHttpUrl.host)
                .port(baseHttpUrl.port)
                .build()
            //获取处理后的新newRequest
            val newRequest = builder.url(newHttpUrl).build()
            Logs.d("newHttpUrl=${newRequest.url}")
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }
}