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
 * 该拦截器的作用是：统一页面跳转时，判断用户是否已经登录，
 * 将业务层判断用户是否登录的逻辑统一到这里，业务层就不需要做if else判断了
 * @Author: wangyuebin
 * @Date: 2021/9/17 2:25 下午
 */
@Interceptor(name = "login", priority = 1)
class LoginInterceptor : IInterceptor {

    /**
     * 该集合保存的是需要登录成功之后才跳转的页面，也就是有@RequireLogin注解的页面
     */
    private var pageList = mutableListOf<String>()


    override fun init(context: Context?) {
        pageList.add("com.bbgo.module_compose.activity.ComposeActivity")
    }


    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        // 判断哪些页面需要登录 (在整个应用中，有些页面需要登录，有些是不需要登录的)
        if (pageList.contains(postcard.destination.canonicalName)) {
            if (AppUtil.isLogin) { // 如果已经登录了，则默认不做任何处理
                callback.onContinue(postcard)
            } else { // 未登录，拦截
                callback.onInterrupt(null)
            }
        } else {
            callback.onContinue(postcard)
        }
    }
}