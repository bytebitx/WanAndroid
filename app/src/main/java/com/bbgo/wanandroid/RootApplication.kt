package com.bbgo.wanandroid

import com.bbgo.common_base.BaseApplication
import com.didichuxing.doraemonkit.DoKit
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RootApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        DoKit.Builder(this)
            .build()
    }
}