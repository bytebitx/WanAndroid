package com.bbgo.common_base

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.android.arouter.thread.DefaultPoolExecutor
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.pool.AppThreadPoolExecutor
import com.hjq.permissions.XXPermissions
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        MMKV.initialize(this)
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
        initLogConfig()
        // 当前项目是否已经适配了分区存储的特性
        XXPermissions.setScopedStorage(true)
    }

    private fun initLogConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
            .methodCount(2)         // (Optional) How many method line to show. Default 2
            .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag(Constants.APP_TAG)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    companion object {
        private var context: BaseApplication? = null
        fun getContext(): Context {
            return context!!
        }
    }
}