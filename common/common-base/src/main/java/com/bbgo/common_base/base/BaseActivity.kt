package com.bbgo.common_base.base

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bbgo.common_base.R
import com.bbgo.common_base.util.ActivityCollector
import com.bbgo.common_base.util.Logs
import com.bbgo.common_base.util.SettingUtil
import com.bbgo.common_base.util.StatusBarUtil
import com.bbgo.library_statusbar.NotchScreenManager
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType

/**
 *  author: wangyb
 *  date: 2021/5/20 10:03 上午
 *  description: todo
 */

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    /**
     * 日志输出标志
     */
    protected val TAG: String = this.javaClass.simpleName

    protected lateinit var binding: VB

    private val inflate = "inflate"

    /**
     * 当前Activity的实例。
     */
    private var activity: Activity? = null

    /** 当前Activity的弱引用，防止内存泄露  */
    private var activityWR: WeakReference<Activity>? = null

    /**
     * theme color
     */
    private var themeColor: Int = SettingUtil.getColor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotchScreenManager.getInstance().setDisplayInNotch(this)
        initColor()

        activity = this
        activityWR = WeakReference(activity!!)
        ActivityCollector.pushTask(activityWR)
        initVb()
        if (this::binding.isInitialized) {
            setContentView(binding.root)
            initView()
            observe()
            initData()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initVb() {
        kotlin.runCatching {
            val type = javaClass.genericSuperclass
            if (type is ParameterizedType) {
                val vbCls = type.actualTypeArguments[0] as Class<VB>
                when {
                    // 当页面中填写的是ViewBinding则表示不需要使用ViewBinding功能
                    ViewBinding::class.java.isAssignableFrom(vbCls) && vbCls != ViewBinding::class.java -> {
                        vbCls.getDeclaredMethod(inflate, LayoutInflater::class.java).let {
                            binding = it.invoke(null, layoutInflater) as VB
                        }
                    }
                }
            } else {
                throw IllegalArgumentException("Parameter err! Generic ViewBinding fail!")
            }
        }.onFailure {
            Logs.e(it, "$this initVb error, ${it.message} ")
        }
    }

    abstract fun initView()
    abstract fun observe()
    abstract fun initData()

    open fun initColor() {
        themeColor = if (!SettingUtil.getIsNightMode()) {
            SettingUtil.getColor()
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this, themeColor, 0)
        if (this.supportActionBar != null) {
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(themeColor))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
        ActivityCollector.removeTask(activityWR)
    }
}