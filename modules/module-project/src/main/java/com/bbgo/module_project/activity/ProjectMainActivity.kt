package com.bbgo.module_project.activity

import android.os.Bundle
import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_project.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectMainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}