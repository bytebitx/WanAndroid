package com.bytebitx.project.activity

import com.bytebitx.base.base.BaseActivity
import com.bytebitx.project.databinding.FragmentProjectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectMainActivity : BaseActivity<FragmentProjectBinding>() {

    override fun inflateViewBinding() = FragmentProjectBinding.inflate(layoutInflater)
}