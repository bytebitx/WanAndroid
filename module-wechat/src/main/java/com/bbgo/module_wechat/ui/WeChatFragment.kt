package com.bbgo.module_wechat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.module_wechat.viewmodel.WeChatViewModel
import com.bbgo.module_wechat.bean.WXArticle
import com.bbgo.module_wechat.databinding.FragmentWechatBinding
import com.bbgo.module_wechat.util.InjectorUtil
import com.google.android.material.tabs.TabLayoutMediator

/**
 *  author: wangyb
 *  date: 2021/5/20 3:04 下午
 *  description: todo
 */
@Route(path = Constants.NAVIGATION_TO_WECHAT_FRG)
class WeChatFragment : BaseFragment() {

    private var _binding: FragmentWechatBinding? = null
    private val binding get() = _binding!!
    private val weChatViewModel: WeChatViewModel by activityViewModels{
        InjectorUtil.getWeChatViewModelFactory()
    }

    /**
     * datas
     */
    private val weChatDatas = mutableListOf<WXArticle>()

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: WeChatPagerAdapter by lazy {
        WeChatPagerAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWechatBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun lazyLoad() {
        weChatViewModel.getWXChapters()
    }

    override fun initView() {
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = weChatDatas[position].name
        }.attach()
    }

    override fun observeViewModel() {
        observe(weChatViewModel.wxChapterLiveData, ::handleWxChapter)
    }

    private fun handleWxChapter(status: Resource<List<WXArticle>>) {
        when(status) {
            is Resource.DataError -> {
                showToast(status.errorMsg!!)
            }
            else -> {
                status.data?.let {
                    weChatDatas.addAll(it)
                    viewPagerAdapter.setList(weChatDatas)
                    binding.viewPager.offscreenPageLimit = weChatDatas.size
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "WeChatFragment"
    }
}