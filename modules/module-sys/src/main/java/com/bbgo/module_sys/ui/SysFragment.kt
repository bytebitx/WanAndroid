package com.bbgo.module_sys.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.module_sys.R
import com.bbgo.module_sys.databinding.FragmentSysBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 *  author: wangyb
 *  date: 2021/5/20 3:04 下午
 *  description: todo
 */
@Route(path = Constants.NAVIGATION_TO_SYS_FRG)
class SysFragment : BaseFragment() {

    private val titleList = mutableListOf<String>()

    private var _binding: FragmentSysBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: SysPagerAdapter by lazy {
        SysPagerAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSysBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun lazyLoad() {
    }

    override fun initView() {
        titleList.add(getString(R.string.knowledge_system))
        titleList.add(getString(R.string.navigation))
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun observeViewModel() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "SysFragment"
    }
}