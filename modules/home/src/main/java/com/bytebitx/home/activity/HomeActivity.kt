package com.bytebitx.home.activity

import com.bytebitx.base.base.BaseActivity
import com.bytebitx.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<FragmentHomeBinding>() {
    override fun inflateViewBinding() = FragmentHomeBinding.inflate(layoutInflater)
}