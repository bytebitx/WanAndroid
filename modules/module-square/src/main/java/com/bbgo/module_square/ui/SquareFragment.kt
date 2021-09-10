package com.bbgo.module_square.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.bus.BusKey
import com.bbgo.common_base.bus.LiveDataBus
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.event.MessageEvent
import com.bbgo.common_base.event.ScrollEvent
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.widget.SpaceItemDecoration
import com.bbgo.common_service.collect.CollectService
import com.bbgo.module_square.R
import com.bbgo.module_square.bean.ArticleDetail
import com.bbgo.module_square.databinding.SquareFragmentHomeBinding
import com.bbgo.module_square.viewmodel.SquareViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 *  author: wangyb
 *  date: 2021/5/20 3:00 下午
 *  description: todo
 */
@Route(path = RouterPath.Square.PAGE_SQUARE)
@AndroidEntryPoint
class SquareFragment : BaseFragment() {

    private var _binding: SquareFragmentHomeBinding? = null
    private val binding get() = _binding!!

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SquareFragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun initView() {
        ARouter.getInstance().inject(this)

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
        LiveDataBus.get().with(BusKey.COLLECT, MessageEvent::class.java).observe(this) {
            if (it.indexPage == Constants.FragmentIndex.SQUARE_INDEX) {
                handleCollect(it)
            }
        }
        LiveDataBus.get().with(BusKey.SCROLL_TOP, ScrollEvent::class.java).observe(this) {
            if (it.index == 3) {
                scrollToTop()
            }
        }
    }

    override fun lazyLoad() {
        squareViewModel.getSquareList(0)
    }

    override fun observeViewModel() {
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

            }
            else -> {
                articles.data?.let {
                    articleList.addAll(it)
                    if (isRefresh) {
                        squareAdapter.setList(it)
                    } else {
                        squareAdapter.addData(it)
                    }
                }
                binding.swipeRefreshLayout.isRefreshing = false
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