package com.bbgo.module_wechat.activity

import android.os.Bundle
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.library_statusbar.NotchScreenManager
import com.bbgo.module_wechat.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeChatMainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}