package com.bbgo.module_home.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_home.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_home)
    }
}