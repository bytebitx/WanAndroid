package com.bbgo.module_wechat.ui

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.databinding.LayoutLoadingBinding
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.showToast
import com.bbgo.module_wechat.bean.WXArticle
import com.bbgo.module_wechat.databinding.FragmentWechatBinding
import com.bbgo.module_wechat.viewmodel.WeChatViewModel
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

    override fun initView() {
        loadingBinding = LayoutLoadingBinding.bind(binding.root)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = weChatDatas[position].name
        }.attach()
    }

    override fun observe() {
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

    private fun handleWxChapter(status: Resource<List<WXArticle>>) {
        when(status) {
            is Resource.Loading -> {
                logD("Resource.Loading")
                loadingBinding.progressBar.visibility = View.VISIBLE
            }
            is Resource.DataError -> {
                loadingBinding.progressBar.visibility = View.GONE
                showToast(status.errorMsg!!)
            }
            else -> {
                loadingBinding.progressBar.visibility = View.GONE
                status.data?.let {
                    weChatDatas.addAll(it)
                    viewPagerAdapter.setList(weChatDatas)
                    binding.viewPager.offscreenPageLimit = weChatDatas.size
                }
            }
        }
    }

    companion object {
        private const val TAG = "WeChatFragment"
    }
}