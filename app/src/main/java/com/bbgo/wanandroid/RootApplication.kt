package com.bbgo.wanandroid

import androidx.databinding.library.BuildConfig
import com.bbgo.common_base.BaseApplication
import com.didichuxing.doraemonkit.DoKit
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RootApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            DoKit.Builder(this)
                .build()
        }

    }
}