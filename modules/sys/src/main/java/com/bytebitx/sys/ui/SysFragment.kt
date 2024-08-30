package com.bytebitx.sys.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.bytebitx.base.base.BaseFragment
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.sys.R
import com.bytebitx.sys.databinding.FragmentSysBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 *  author: wangyb
 *  date: 2021/5/20 3:04 下午
 *  description: todo
 */
@Route(path = RouterPath.Sys.PAGE_SYS)
@AndroidEntryPoint
class SysFragment : BaseFragment<FragmentSysBinding>() {

    private val titleList = mutableListOf<String>()

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: SysPagerAdapter by lazy {
        SysPagerAdapter(this)
    }

    override fun lazyLoad() {
    }

    override fun initObserver() {
    }

    override fun initView() {
        titleList.add(getString(R.string.knowledge_system))
        titleList.add(getString(R.string.navigation))
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }
}