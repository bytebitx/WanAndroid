package com.bytebitx.base.base

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bytebitx.base.util.ActivityCollector
import com.bytebitx.base.util.statusbar.NotchScreenManager
import java.lang.ref.WeakReference

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
        binding = inflateViewBinding()
        setContentView(binding.root)
        NotchScreenManager.getInstance().setDisplayInNotch(this)
        activity = this
        activityWR = WeakReference(activity!!)
        ActivityCollector.pushTask(activityWR)
    }

    abstract fun inflateViewBinding(): VB

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