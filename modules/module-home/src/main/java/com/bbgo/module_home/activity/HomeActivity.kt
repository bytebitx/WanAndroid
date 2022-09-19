package com.bbgo.module_home.activity

import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_home.R
import com.bbgo.module_home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<FragmentHomeBinding>() {
    override fun inflateViewBinding() = FragmentHomeBinding.inflate(layoutInflater)
}