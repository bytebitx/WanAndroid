package com.bytebitx.base

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.elvishew.xlog.BuildConfig
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
    }


    companion object {
        private var context: BaseApplication? = null
        @JvmStatic
        fun getContext(): Context {
            return context!!
        }
    }
}