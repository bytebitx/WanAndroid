package com.bbgo.module_project.activity

import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_project.R
import com.bbgo.module_project.databinding.FragmentProjectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProjectMainActivity : BaseActivity<FragmentProjectBinding>() {

    override fun inflateViewBinding() = FragmentProjectBinding.inflate(layoutInflater)
}