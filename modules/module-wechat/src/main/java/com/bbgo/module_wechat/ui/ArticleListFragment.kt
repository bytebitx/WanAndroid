package com.bbgo.module_wechat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.bus.BusKey
import com.bbgo.common_base.bus.LiveDataBus
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.event.MessageEvent
import com.bbgo.common_base.event.ScrollEvent
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.widget.SpaceItemDecoration
import com.bbgo.common_service.collect.CollectService
import com.bbgo.module_wechat.R
import com.bbgo.module_wechat.bean.ArticleDetail
import com.bbgo.module_wechat.databinding.FragmentArticleListBinding
import com.bbgo.module_wechat.viewmodel.WeChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by wangyb
 */
@AndroidEntryPoint
class ArticleListFragment : BaseFragment() {

    companion object {
        fun getInstance(cid: Int): ArticleListFragment {
            val fragment = ArticleListFragment()
            val args = Bundle()
            args.putInt(Constants.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentArticleListBinding? = null
    private val binding get() = _binding!!

    @Autowired
    lateinit var collectService: CollectService

    /**
     * 在这里不使用委托方式by 来创建viewmodel，是因为该方式只会创建一个viewmodel实例
     * 多个fragment需要多个viewmodel实例
     */
    private lateinit var weChatViewModel: WeChatViewModel

    /**
     * cid
     */
    private var cid: Int = 0

    /**
     * 是否是下拉刷新
     */
    private var isRefresh = true

    /**
     * datas
     */
    private val articleList = mutableListOf<ArticleDetail>()

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * Adapter
     */
    private val mAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter(articleList)
    }

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        binding.swipeRefreshLayout.isRefreshing = false
        isRefresh = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun initView() {
        ARouter.getInstance().inject(this)

        cid = arguments?.getInt(Constants.CONTENT_CID_KEY) ?: 0
        binding.swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
        binding.recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        mAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                val article = articleList[position]
                ARouter.getInstance().build(RouterPath.Content.PAGE_CONTENT)
                    .withString(Constants.POSITION, position.toString())
                    .withString(Constants.CONTENT_ID_KEY, article.id.toString())
                    .withString(Constants.CONTENT_TITLE_KEY, article.title)
                    .withString(Constants.CONTENT_URL_KEY, article.link)
                    .withString(Constants.COLLECT, article.collect.toString())
                    .navigation()
            }
            addChildClickViewIds(R.id.iv_like)
            setOnItemChildClickListener { adapter, view, position ->
                if (view.id == R.id.iv_like) {
                    val article = articleList[position]
                    if (article.collect) {
                        collectService.unCollect(
                            Constants.FragmentIndex.WECHAT_INDEX,
                            position,
                            articleList[position].id
                        )
                        return@setOnItemChildClickListener
                    }
                    collectService.collect(
                        Constants.FragmentIndex.WECHAT_INDEX,
                        position,
                        articleList[position].id
                    )
                }
            }
        }

        weChatViewModel = ViewModelProvider(this).get(WeChatViewModel::class.java)

        initBus()
    }

    override fun lazyLoad() {
        weChatViewModel.getWXArticles(cid, 0)
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                weChatViewModel.wxArticlesUiState.collectLatest {
                    handleInfo(it)
                }
            }
        }
    }

    /**
     * 初始化事件总线，和eventbus效果相同
     */
    private fun initBus() {
        LiveDataBus.get().with(BusKey.COLLECT, MessageEvent::class.java).observe(this) {
            if (it.indexPage == Constants.FragmentIndex.WECHAT_INDEX) {
                handleCollect(it)
            }
        }
        LiveDataBus.get().with(BusKey.SCROLL_TOP, ScrollEvent::class.java).observe(this) {
            if (it.index == 1) {
                scrollToTop()
            }
        }
    }

    private fun handleCollect(event: MessageEvent) {
        when (event.type) {
            Constants.CollectType.UNKNOWN -> {
                ARouter.getInstance().build(RouterPath.LoginRegister.PAGE_LOGIN).navigation()
            }
            else -> {
                if (articleList.isEmpty()) {
                    return
                }
                if (event.type == Constants.CollectType.COLLECT) {
                    showToast(getString(R.string.collect_success))
                    articleList[event.position].collect = true
                    mAdapter.setList(articleList)
                    return
                }
                articleList[event.position].collect = false
                mAdapter.setList(articleList)
                showToast(getString(R.string.cancel_collect_success))
            }
        }
    }

    private fun handleInfo(status: Resource<MutableList<ArticleDetail>>) {
        when(status) {
            is Resource.Loading -> {
            }
            else -> {
                status.data?.let {
                    articleList.clear()
                    articleList.addAll(it)
                    mAdapter.run {
                        if (isRefresh) {
                            setList(articleList)
                        } else {
                            addData(articleList)
                        }
                    }
                }
            }
        }
    }

    private fun scrollToTop() {
        binding.recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}