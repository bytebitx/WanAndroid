package com.bbgo.module_content.ext

import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebView
import com.bbgo.module_content.R
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient

/**
 * Created by chenxz on 2018/4/22.
 */

/**
 * getAgentWeb
 */
fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams,
    webView: WebView,
    webViewClient: WebViewClient?,
    webChromeClient: WebChromeClient?,
    indicatorColor: Int
): AgentWeb = AgentWeb.with(activity)//传入Activity or Fragment
        .setAgentWebParent(webContent, 1, layoutParams)//传入AgentWeb 的父控件
        .useDefaultIndicator(indicatorColor, 2)// 使用默认进度条
        .setWebView(webView)
        .setWebViewClient(webViewClient)
        .setWebChromeClient(webChromeClient)
        .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
        .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
        .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
        .interceptUnkownUrl()
        .createAgentWeb()//
        .ready()
        .go(this)
