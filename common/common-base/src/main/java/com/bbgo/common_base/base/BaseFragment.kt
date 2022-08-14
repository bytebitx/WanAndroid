package com.bbgo.common_base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 *  author: wangyb
 *  date: 2021/5/20 3:01 下午
 *  description: todo
 */
abstract class BaseFragment(resId: Int) : Fragment(resId) {

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    private var isLoaded = false

    /**
     * 防止回退到上一级页面时还会init view的问题
     */
    private var isInitializedRootView = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isInitializedRootView) return
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
        isInitializedRootView = true
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyLoad()
            isLoaded = true
        }
    }

    /**
     * 初始化 View
     */
    abstract fun initView()
    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    abstract fun observe()
}