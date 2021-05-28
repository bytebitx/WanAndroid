package com.bbgo.common_base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 *  author: wangyb
 *  date: 2021/5/20 3:01 下午
 *  description: todo
 */
abstract class BaseFragment : Fragment() {

    private var isLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
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

    open fun observeViewModel() {
    }

}