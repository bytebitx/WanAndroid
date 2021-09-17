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
    override fun init(context: Context?) {
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        if (AppUtil.isLogin) { // 如果已经登录了，则默认不做任何处理
            callback.onContinue(postcard)
        } else {
            // 判断哪些页面需要登录 (在整个应用中，有些页面需要登录，有些是不需要登录的)
            if (postcard.path == RouterPath.Compose.PAGE_COMPOSE) {
                callback.onInterrupt(null)
            } else { // 不是需要登录的页面，不做任何处理
                callback.onContinue(postcard)
            }
        }
    }
}