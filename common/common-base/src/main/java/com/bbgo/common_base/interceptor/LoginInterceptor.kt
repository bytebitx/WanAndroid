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

    /**
     * 在该项目中，也可以使用AppUtils.isLogin来直接判断是否登录，但是这样就耦合了AppUtils
     * 如果不想耦合，就可以使用RefletionUtils.getLoginField()来判断是否登录，不过
     * 前提是需要为AppUtils的isLogin变量增加@InjectLogin。这样做的好处就是：
     * 可以将LoginInterceptor和RefletionUtils单独拿出来作为一个lib。
     */
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        // 判断哪些页面需要登录 (在整个应用中，有些页面需要登录，有些是不需要登录的)
        if (pageList.contains(postcard.destination.canonicalName)) {
            if (RefletionUtils.getLoginField()) { // 如果已经登录了，则默认不做任何处理
                callback.onContinue(postcard)
            } else { // 未登录，拦截
                callback.onInterrupt(null)
            }
        } else {
            callback.onContinue(postcard)
        }
    }
}