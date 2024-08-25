package com.bytebitx.square.activity

import com.bytebitx.base.base.BaseActivity
import com.bytebitx.square.databinding.FragmentSquareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquareMainActivity : BaseActivity<FragmentSquareBinding>() {
    override fun inflateViewBinding() = FragmentSquareBinding.inflate(layoutInflater)
}