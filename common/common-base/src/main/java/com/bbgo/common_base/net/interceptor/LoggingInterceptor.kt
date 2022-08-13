package com.bbgo.common_base.net.interceptor

import com.bbgo.common_base.util.Logs
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *  author: wangyb
 *  date: 2021/5/28 10:19 上午
 *  description: todo
 */
class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        Logs.d("Sending request: ${request.url} \n ${request.headers}")

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Logs.d("Received response for  ${response.request.url} in ${(t2 - t1) / 1e6} ms\n${response.headers}")
        return response
    }

    companion object {
        const val TAG = "LoggingInterceptor"
    }
}