package com.bbgo.module_wechat.activity

import com.bbgo.common_base.base.BaseActivity
import com.bbgo.module_wechat.R
import com.bbgo.module_wechat.databinding.FragmentWechatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeChatMainActivity : BaseActivity<FragmentWechatBinding>() {
    override fun inflateViewBinding() = FragmentWechatBinding.inflate(layoutInflater)
}