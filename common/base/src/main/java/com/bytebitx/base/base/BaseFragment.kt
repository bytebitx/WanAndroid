package com.bytebitx.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bytebitx.base.util.log.Logs
import java.lang.reflect.ParameterizedType

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
        return rootView ?: run {
            initVB()
            rootView = _binding?.root
            rootView
        }
    }

    private fun initVB() {
        runCatching {
            val type = javaClass.genericSuperclass
            @Suppress("UNCHECKED_CAST")
            val clazz: Class<VB> = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val method = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java)
            @Suppress("UNCHECKED_CAST")
            _binding = method.invoke(this, layoutInflater)!! as VB
        }.onFailure {
            Logs.e(it, "init view binding error")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isInitializedRootView) return
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        isInitializedRootView = true
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyLoad()
            isLoaded = true
        }
    }

    abstract fun initView()
    abstract fun lazyLoad()
    abstract fun initObserver()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}