package com.bbgo.common_base.net.interceptor

import com.bbgo.common_base.constants.HttpConstant
import com.bbgo.common_base.ext.Prefs
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val builder = request.newBuilder()

        val domain = request.url.host
        val url = request.url.toString()
        if (domain.isNotEmpty() && (url.contains(HttpConstant.COLLECTIONS_WEBSITE)
                        || url.contains(HttpConstant.UNCOLLECTIONS_WEBSITE)
                        || url.contains(HttpConstant.ARTICLE_WEBSITE)
                        || url.contains(HttpConstant.TODO_WEBSITE)
                        || url.contains(HttpConstant.COIN_WEBSITE))) {
            val spDomain: String = Prefs.getString(domain, "")
            val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
            if (cookie.isNotEmpty()) {
                // 将 Cookie 添加到请求头
                builder.addHeader(HttpConstant.COOKIE_NAME, cookie)
            }
        }

        return chain.proceed(builder.build())
    }

}