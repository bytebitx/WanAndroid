package com.bytebitx.square.ui

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bytebitx.base.base.BaseFragment
import com.bytebitx.base.constants.Constants
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.base.databinding.LayoutLoadingBinding
import com.bytebitx.base.event.MessageEvent
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.ext.observe
import com.bytebitx.base.ext.showToast
import com.bytebitx.base.widget.SpaceItemDecoration
import com.bytebitx.service.collect.CollectService
import com.bytebitx.square.R
import com.bytebitx.square.bean.ArticleDetail
import com.bytebitx.square.databinding.FragmentSquareBinding
import com.bytebitx.square.viewmodel.SquareViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 2021/5/20 3:00 下午
 *  description: todo
 */
@Route(path = RouterPath.Square.PAGE_SQUARE)
@AndroidEntryPoint
class SquareFragment : BaseFragment<FragmentSquareBinding>() {

    private lateinit var loadingBinding: LayoutLoadingBinding

    @Autowired
    lateinit var collectService: CollectService

    @Inject
    lateinit var squareViewModel: SquareViewModel

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
     * datas
     */
    private val articleList = mutableListOf<ArticleDetail>()

    /**
     * Square Adapter
     */
    private val squareAdapter: SquareAdapter by lazy {
        SquareAdapter(articleList)
    }

    /**
     * is Refresh
     */
    private var isRefresh = true

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        squareViewModel.getSquareList(0)
    }

    override fun initView() {
        ARouter.getInstance().inject(this)

        loadingBinding = LayoutLoadingBinding.bind(binding.root)
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        binding.recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = squareAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }
        squareAdapter.run {
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
                            Constants.FragmentIndex.SQUARE_INDEX,
                            position,
                            articleList[position].id
                        )
                        return@setOnItemChildClickListener
                    }
                    collectService.collect(
                        Constants.FragmentIndex.SQUARE_INDEX,
                        position,
                        articleList[position].id
                    )
                }
            }
        }

        initBus()
    }

    /**
     * 初始化事件总线，和eventbus效果相同
     */
    private fun initBus() {
//        LiveDataBus.get().with(BusKey.COLLECT, MessageEvent::class.java).observe(this) {
//            if (it.indexPage == Constants.FragmentIndex.SQUARE_INDEX) {
//                handleCollect(it)
//            }
//        }
//        LiveDataBus.get().with(BusKey.SCROLL_TOP, ScrollEvent::class.java).observe(this) {
//            if (it.index == 3) {
//                scrollToTop()
//            }
//        }
    }

    override fun lazyLoad() {
        squareViewModel.getSquareList(0)
    }

    override fun initObserver() {
        observe(squareViewModel.articleLiveData, ::handleInfo)
    }

    private fun handleCollect(event: MessageEvent) {
        when (event.type) {
            Constants.CollectType.UNKNOWN -> {
                ARouter.getInstance().build(RouterPath.LoginRegister.PAGE_LOGIN).navigation()
            }
            else -> {
                if (event.type == Constants.CollectType.COLLECT) {
                    showToast(getString(R.string.collect_success))
                    articleList[event.position].collect = true
                    squareAdapter.setList(articleList)
                    return
                }
                articleList[event.position].collect = false
                squareAdapter.setList(articleList)
                showToast(getString(R.string.cancel_collect_success))
            }
        }
    }

    private fun handleInfo(articles: Resource<List<ArticleDetail>>) {
        when(articles) {
            is Resource.Loading -> {
                loadingBinding.progressBar.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                loadingBinding.progressBar.visibility = View.GONE
                articleList.addAll(articles.data)
                if (isRefresh) {
                    squareAdapter.setList(articles.data)
                } else {
                    squareAdapter.addData(articles.data)
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }

            else -> {}
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
}