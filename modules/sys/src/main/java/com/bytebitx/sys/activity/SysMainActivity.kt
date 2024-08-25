package com.bytebitx.sys.activity

import com.bytebitx.base.base.BaseActivity
import com.bytebitx.sys.databinding.FragmentSysBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SysMainActivity : BaseActivity<FragmentSysBinding>() {
    override fun inflateViewBinding() = FragmentSysBinding.inflate(layoutInflater)
}