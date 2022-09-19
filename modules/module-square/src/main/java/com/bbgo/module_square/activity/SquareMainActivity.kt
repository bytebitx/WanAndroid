package com.bbgo.module_square.activity

import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_square.R
import com.bbgo.module_square.databinding.FragmentSquareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquareMainActivity : BaseActivity<FragmentSquareBinding>() {
    override fun inflateViewBinding() = FragmentSquareBinding.inflate(layoutInflater)
}