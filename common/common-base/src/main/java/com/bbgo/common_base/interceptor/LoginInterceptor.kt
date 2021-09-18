package com.bbgo.common_base.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.util.AppUtil

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/9/17 2:25 下午
 */
@Interceptor(name = "login", priority = 1)
class LoginInterceptor : IInterceptor {

    /**
     * 该集合保存的是需要登录成功之后才跳转的页面，也就是有@RequireLogin注解的页面
     */
    private lateinit var pageList: List<String>

    private var isLogin: Boolean = false

    override fun init(context: Context?) {
        pageList = RefletionUtils.getRequireLoginPages()
        isLogin = RefletionUtils.getLoginField()
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        if (RefletionUtils.getLoginField()) { // 如果已经登录了，则默认不做任何处理
            callback.onContinue(postcard)
        } else {
            if (pageList.isEmpty()) {
                callback.onContinue(postcard)
                return
            }
            // 判断哪些页面需要登录 (在整个应用中，有些页面需要登录，有些是不需要登录的)
            if (pageList.contains(postcard.destination.canonicalName)) {
                callback.onInterrupt(null)
            } else { // 不是需要登录的页面，不做任何处理
                callback.onContinue(postcard)
            }
        }
    }
}