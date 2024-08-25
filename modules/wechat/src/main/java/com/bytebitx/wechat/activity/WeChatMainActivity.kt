package com.bytebitx.wechat.activity

import com.bytebitx.base.base.BaseActivity
import com.bytebitx.wechat.databinding.FragmentWechatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeChatMainActivity : BaseActivity<FragmentWechatBinding>() {
    override fun inflateViewBinding() = FragmentWechatBinding.inflate(layoutInflater)
}