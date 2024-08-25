package com.bytebitx.wechat.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.bytebitx.base.base.BaseFragment
import com.bytebitx.base.bus.BusKey
import com.bytebitx.base.bus.LiveDataBus
import com.bytebitx.base.constants.Constants
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.base.event.MessageEvent
import com.bytebitx.base.event.ScrollEvent
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.ext.showToast
import com.bytebitx.base.util.log.Logs
import com.bytebitx.base.widget.SpaceItemDecoration
import com.bytebitx.service.collect.CollectService
import com.bytebitx.wechat.R
import com.bytebitx.wechat.bean.ArticleDetail
import com.bytebitx.wechat.databinding.FragmentArticleListBinding
import com.bytebitx.wechat.viewmodel.WeChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by wangyb
 */
@AndroidEntryPoint
class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    companion object {
        fun getInstance(cid: Int): ArticleListFragment {
            val fragment = ArticleListFragment()
            val args = Bundle()
            args.putInt(Constants.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

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


    override fun initView() {
        binding.recyclerView.post {
            Logs.d("width = ${binding.recyclerView.width}")
            Logs.d("height = ${binding.recyclerView.height}")
        }
        Handler(Looper.getMainLooper()).post {
            Logs.d("width handler = ${binding.recyclerView.width}")
            Logs.d("height handler = ${binding.recyclerView.height}")
        }
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

    override fun observe() {
        lifecycleScope.launch {
            /**
             * 如果只收集一个stateFlow的数据，则可以使用flowWithLifecycle
             * 操作符决定生命周期
             *
             */
            weChatViewModel.wxArticlesUiState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    handleInfo(it)
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
        if (status !is Resource.Success) return
        articleList.clear()
        articleList.addAll(status.data)
        mAdapter.run {
            if (isRefresh) {
                setList(articleList)
            } else {
                addData(articleList)
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

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentArticleListBinding.inflate(inflater, container, false)
}