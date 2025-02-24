package com.bytebitx.wechat.ui

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.bytebitx.base.base.BaseFragment
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.base.databinding.LayoutLoadingBinding
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.ext.showToast
import com.bytebitx.wechat.bean.WXArticle
import com.bytebitx.wechat.databinding.FragmentWechatBinding
import com.bytebitx.wechat.viewmodel.WeChatViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *  author: wangyb
 *  date: 2021/5/20 3:04 下午
 *  description: todo
 */
@Route(path = RouterPath.WeChat.PAGE_WECHAT)
@AndroidEntryPoint
class WeChatFragment : BaseFragment<FragmentWechatBinding>() {

    private lateinit var loadingBinding: LayoutLoadingBinding

    private val weChatViewModel: WeChatViewModel by activityViewModels()

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

    override fun lazyLoad() {
        weChatViewModel.getWXChapters()
    }

    override fun initObserver() {
        /**
         * 如果只收集一个stateFlow的数据，则可以使用flowWithLifecycle
         * 操作符决定生命周期
         *
         */
        lifecycleScope.launch {
            weChatViewModel.wxChapterUiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    handleWxChapter(it)
                }
        }
    }

    override fun initView() {
        loadingBinding = LayoutLoadingBinding.bind(binding.root)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = weChatDatas[position].name
        }.attach()
    }

    private fun handleWxChapter(status: Resource<List<WXArticle>>) {
        when(status) {
            is Resource.Loading -> {
                loadingBinding.progressBar.visibility = View.VISIBLE
            }
            is Resource.Error -> {
                loadingBinding.progressBar.visibility = View.GONE
                showToast(status.exception.toString())
            }
            is Resource.Success -> {
                loadingBinding.progressBar.visibility = View.GONE
                weChatDatas.addAll(status.data)
                viewPagerAdapter.setList(weChatDatas)
                binding.viewPager.offscreenPageLimit = weChatDatas.size
            }
        }
    }
}