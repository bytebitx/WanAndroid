package com.bbgo.common_base.net.interceptor

import android.os.Build
import com.bbgo.common_base.ext.screenPixel
import com.bbgo.common_base.util.AppUtil
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  author: wangyb
 *  date: 2021/5/28 10:20 上午
 *  description: todo
 */
class BasicParamsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val url = originalHttpUrl.newBuilder().apply {
            addQueryParameter("udid", AppUtil.getDeviceSerial())
            addQueryParameter("vc", AppUtil.eyepetizerVersionCode.toString())
            addQueryParameter("vn", AppUtil.eyepetizerVersionName)
            addQueryParameter("size", screenPixel())
            addQueryParameter("deviceModel", AppUtil.deviceModel)
            addQueryParameter("first_channel", AppUtil.deviceBrand)
            addQueryParameter("last_channel", AppUtil.deviceBrand)
            addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
        }.build()
        val request = originalRequest.newBuilder().url(url).method(originalRequest.method,
            originalRequest.body
        ).build()
        return chain.proceed(request)
    }
}