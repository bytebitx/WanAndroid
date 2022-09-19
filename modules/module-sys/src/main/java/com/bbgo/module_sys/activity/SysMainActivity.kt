package com.bbgo.module_sys.activity

import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_sys.R
import com.bbgo.module_sys.databinding.FragmentSysBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SysMainActivity : BaseActivity<FragmentSysBinding>() {
    override fun inflateViewBinding() = FragmentSysBinding.inflate(layoutInflater)
}