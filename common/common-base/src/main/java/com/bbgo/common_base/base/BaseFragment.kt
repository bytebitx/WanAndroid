package com.bbgo.common_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bbgo.common_base.util.log.Logs
import java.lang.reflect.ParameterizedType

/**
 *  author: wangyb
 *  date: 2021/5/20 3:01 下午
 *  description: todo
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    private var isLoaded = false

    /**
     * 防止回退到上一级页面时还会init view的问题
     */
    private var isInitializedRootView = false
    private var _binding: VB? = null

    protected val binding get() = _binding!!
    private var rootView: View? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            kotlin.runCatching {
                val type = javaClass.genericSuperclass as ParameterizedType
                val clazz2 = type.actualTypeArguments[0] as Class<VB>
                val method = clazz2.getMethod("inflate", LayoutInflater::class.java)
                _binding = method.invoke(null, layoutInflater) as VB
                rootView = _binding?.root
            }.onFailure {
                Logs.e(it, "init root view error = ${it.message}")
            }
        }
        return rootView
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

    /**
     * 初始化 View
     */
    abstract fun initView()
    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    open fun observe() {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        rootView = null
    }
}