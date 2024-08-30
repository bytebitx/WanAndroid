package com.bytebitx.base.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bytebitx.base.util.ActivityCollector
import com.bytebitx.base.util.log.Logs
import com.bytebitx.base.util.statusbar.NotchScreenManager
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

/**
 *  author: wangyb
 *  date: 2021/5/20 10:03 上午
 *  description: todo
 */

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    protected lateinit var binding: VB

    /**
     * 当前Activity的实例。
     */
    private var activity: Activity? = null

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVB()
        if (this::binding.isInitialized) {
            setContentView(binding.root)
            NotchScreenManager.getInstance().setDisplayInNotch(this)
            activity = this
            activityWR = WeakReference(activity!!)
            ActivityCollector.pushTask(activityWR)
            initView()
            initObserver()
            initData()
        }
    }

    private fun initVB() {
        runCatching {
            val type = javaClass.genericSuperclass
            @Suppress("UNCHECKED_CAST")
            val clazz: Class<VB> = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
            val method = clazz.getDeclaredMethod("inflate", LayoutInflater::class.java)
            @Suppress("UNCHECKED_CAST")
            binding = method.invoke(this, layoutInflater)!! as VB
        }.onFailure {
            Logs.e(it, "init view binding error")
        }
    }

    abstract fun initView()
    abstract fun initObserver()
    abstract fun initData()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
        ActivityCollector.removeTask(activityWR)
    }
}