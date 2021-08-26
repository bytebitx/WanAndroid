package com.bbgo.module_content.webclient

import com.just.agentweb.WebViewClient


/**
 * @author chenxz
 * @date 2019/11/24
 * @desc WebClientFactory
 */
object WebClientFactory {

    val JIAN_SHU = "https://www.jianshu.com"

    fun create(url: String): WebViewClient {
        return when {
            url.startsWith(JIAN_SHU) -> JianShuWebClient()
            else -> BaseWebClient()
        }
    }

}