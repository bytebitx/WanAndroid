package com.bbgo.module_wechat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.databinding.LayoutLoadingBinding
import com.bbgo.common_base.ext.Resource
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
class WeChatFragment : BaseFragment() {

    private var _binding: FragmentWechatBinding? = null
    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWechatBinding.inflate(inflater, container, false)
        loadingBinding = LayoutLoadingBinding.bind(binding.root)
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
        lifecycleScope.launch {
            /**
             * flow默认情况下是不管页面处于哪个生命周期都会订阅数据，不会像livedata一样，
             * 在生命周期处于DESTROYED时，移除观察者。因此需要用到repeatOnLifecycle，
             * 让页面处于STARTED及以上的生命周期才订阅数据。
             */
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                weChatViewModel.wxChapterUiState.collectLatest {
                    handleWxChapter(it)
                }
            }
        }
//        observe(weChatViewModel.wxChapterLiveData, ::handleWxChapter)
    }

    private fun handleWxChapter(status: Resource<List<WXArticle>>) {
        when(status) {
            is Resource.Loading -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "WeChatFragment"
    }
}