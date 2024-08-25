package com.bytebitx.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *  author: wangyb
 *  date: 2021/5/20 3:01 下午
 *  description: todo
 */
abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    private var _binding: VB? = null

    protected val binding get() = _binding!!

    private var isLoaded = false

    /**
     * 防止回退到上一级页面时还会init view的问题
     */
    private var isInitializedRootView = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding(inflater, container)
        return _binding?.root
    }

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

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * 初始化 View
     */
    abstract fun initView()
    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    abstract fun observe()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}