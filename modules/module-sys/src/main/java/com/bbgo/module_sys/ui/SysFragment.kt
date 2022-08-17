package com.bbgo.module_sys.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.ext.viewBinding
import com.bbgo.module_sys.R
import com.bbgo.module_sys.databinding.FragmentNavigationBinding
import com.bbgo.module_sys.databinding.FragmentSysBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 *  author: wangyb
 *  date: 2021/5/20 3:04 下午
 *  description: todo
 */
@Route(path = RouterPath.Sys.PAGE_SYS)
@AndroidEntryPoint
class SysFragment : BaseFragment(R.layout.fragment_sys) {

    private val titleList = mutableListOf<String>()

    private val binding by viewBinding(FragmentSysBinding::bind)

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: SysPagerAdapter by lazy {
        SysPagerAdapter(this)
    }

    override fun lazyLoad() {
    }

    override fun observe() {
    }

    override fun initView() {
        titleList.add(getString(R.string.knowledge_system))
        titleList.add(getString(R.string.navigation))
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    companion object {
        private const val TAG = "SysFragment"
    }
}