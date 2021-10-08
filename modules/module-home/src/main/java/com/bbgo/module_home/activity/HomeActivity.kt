package com.bbgo.module_home.activity

import android.os.Bundle
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_home.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}