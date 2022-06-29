package com.bbgo.module_home.ui

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.bus.BusKey
import com.bbgo.common_base.bus.LiveDataBus
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.databinding.LayoutLoadingBinding
import com.bbgo.common_base.event.MessageEvent
import com.bbgo.common_base.event.ScrollEvent
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.util.ImageLoader
import com.bbgo.common_base.widget.SpaceItemDecoration
import com.bbgo.common_service.collect.CollectService
import com.bbgo.module_home.R
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.bean.Banner
import com.bbgo.module_home.databinding.FragmentHomeBinding
import com.bbgo.module_home.databinding.ItemHomeBannerBinding
import com.bbgo.module_home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 *  author: wangyb
 *  date: 2021/5/20 3:00 下午
 *  description: todo
 */
@Route(path = RouterPath.Home.PAGE_HOME)
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object {
        private const val TAG = "HomeFragment"

        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    private lateinit var bannerBinding: ItemHomeBannerBinding
    private lateinit var loadingBinding: LayoutLoadingBinding

    @Inject
    lateinit var homeViewModel: HomeViewModel
    @Autowired(name = RouterPath.Main.SERVICE_COLLECT)
    lateinit var collectService: CollectService

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
     * Home Adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(articleList)
    }

    /**
     * is Refresh
     */
    private var isRefresh = false

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        homeViewModel.getArticles(0)
    }

    /**
     * Banner Adapter
     */
    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            activity?.let { ImageLoader.load(it, feedImageUrl, imageView) }
        }
    }

    override fun initView() {
        bannerBinding = ItemHomeBannerBinding.inflate(layoutInflater)
        loadingBinding = LayoutLoadingBinding.bind(binding.root)

        ARouter.getInstance().inject(this)
        initBus()
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        binding.recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        homeAdapter.run {
            addHeaderView(bannerBinding.root)
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
                            Constants.FragmentIndex.HOME_INDEX,
                            position,
                            articleList[position].id
                        )
                        return@setOnItemChildClickListener
                    }
                    collectService.collect(
                        Constants.FragmentIndex.HOME_INDEX,
                        position,
                        articleList[position].id
                    )
                }
            }
        }
    }

    /**
     * 初始化事件总线，和eventbus效果相同
     */
    private fun initBus() {
        LiveDataBus.get().with(BusKey.COLLECT, MessageEvent::class.java).observe(this) {
            if (it.indexPage == Constants.FragmentIndex.HOME_INDEX) {
                handleCollect(it)
            }
        }
        LiveDataBus.get().with(BusKey.SCROLL_TOP, ScrollEvent::class.java).observe(this) {
            if (it.index == 0) {
                scrollToTop()
            }
        }
    }

    override fun lazyLoad() {
        homeViewModel.getArticles(0)
        homeViewModel.getBanner()
    }

    override fun observe() {
        observe(homeViewModel.articleLiveData, ::handleInfo)
        observe(homeViewModel.bannerLiveData, ::handleBanner)
    }

    private fun handleBanner(status: Resource<List<Banner>>) {
        when(status) {
            is Resource.Loading -> {

            }
            is Resource.DataError -> {
            }
            else -> {
                status.data?.let {

                    val bannerFeedList = ArrayList<String>()
                    val bannerTitleList = ArrayList<String>()
                    it.forEach { banner ->
                        bannerFeedList.add(banner.imagePath)
                        bannerTitleList.add(banner.title)
                    }
                    bannerBinding.banner.setDelegate { banner, imageView, model, position ->

                    }
                    bannerBinding.banner.run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter(bannerAdapter)
                    }
                }
            }
        }
    }

    private fun handleInfo(articles: Resource<MutableList<ArticleDetail>>) {
        when(articles) {
            is Resource.Loading -> {
                loadingBinding.progressBar.visibility = View.VISIBLE
            }
            is Resource.DataError -> {
                loadingBinding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
            }
            else -> {
                loadingBinding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
                articles.data?.let {
                    if (isRefresh) {
                        articleList.clear()
                        articleList.addAll(it)
                        homeAdapter.setList(articleList)
                    } else {
                        articleList.addAll(it)
                        homeAdapter.addData(articleList)
                    }
                }
            }
        }
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
                    homeAdapter.setList(articleList)
                    return
                }
                articleList[event.position].collect = false
                homeAdapter.setList(articleList)
                showToast(getString(R.string.cancel_collect_success))
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
}