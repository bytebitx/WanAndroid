package com.bbgo.module_sys.activity

import android.os.Bundle
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_sys.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SysMainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}