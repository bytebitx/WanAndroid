package com.bbgo.wanandroid.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.util.ImageLoader
import com.bbgo.wanandroid.R
import com.bbgo.wanandroid.bean.ArticleDetail
import com.bbgo.wanandroid.bean.Banner
import com.bbgo.wanandroid.bean.CollectBean
import com.bbgo.wanandroid.collect.viewmodel.CollectViewModel
import com.bbgo.wanandroid.databinding.FragmentHomeBinding
import com.bbgo.wanandroid.databinding.ItemHomeBannerBinding
import com.bbgo.wanandroid.event.MessageEvent
import com.bbgo.wanandroid.home.viewmodel.HomeViewModel
import com.bbgo.wanandroid.login.ui.LoginActivity
import com.bbgo.wanandroid.util.InjectorUtil
import com.bbgo.wanandroid.widget.SpaceItemDecoration
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 *  author: wangyb
 *  date: 2021/5/20 3:00 下午
 *  description: todo
 */
class HomeFragment : BaseFragment() {

    companion object {
        private const val TAG = "HomeFragment"

        fun getInstance(): HomeFragment {
            return HomeFragment()
        }
    }


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bannerBinding: ItemHomeBannerBinding
    private val homeViewModel: HomeViewModel by activityViewModels{
        InjectorUtil.getHomeViewModelFactory()
    }
    private val collectViewModel: CollectViewModel by activityViewModels{
        InjectorUtil.getCollectViewModelFactory()
    }

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
    private var isRefresh = true

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
    }

    /**
     * Banner Adapter
     */
    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            activity?.let { ImageLoader.load(it, feedImageUrl, imageView) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        bannerBinding = ItemHomeBannerBinding.inflate(inflater)
        return _binding?.root
    }

    override fun initView() {
        EventBus.getDefault().register(this)
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
                ARouter.getInstance().build(Constants.NAVIGATION_TO_CONTENT)
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
                        collectViewModel.unCollectArticle(position, articleList[position].id)
                        return@setOnItemChildClickListener
                    }
                    collectViewModel.collectArticle(position, articleList[position].id)
                }
            }
        }
    }

    override fun lazyLoad() {
        homeViewModel.getArticles(0)
        homeViewModel.getBanner()
    }

    override fun observeViewModel() {
        observe(homeViewModel.articleLiveData, ::handleInfo)
        observe(homeViewModel.bannerLiveData, ::handleBanner)
        observe(collectViewModel.collectLiveData, ::handleCollect)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) { /* Do something */
        if (event.type == CollectViewModel.COLLECT) {
            collectViewModel.collectArticle(event.position, event.pageId)
            return
        }
        collectViewModel.unCollectArticle(event.position, event.pageId)
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

            }
            is Resource.DataError -> {
            }
            else -> {
                articles.data?.let {
                    articleList.addAll(it)
                    if (isRefresh) {
                        homeAdapter.setList(articleList)
                    } else {
                        homeAdapter.addData(articleList)
                    }
                }
            }
        }
    }

    private fun handleCollect(status: Resource<CollectBean>) {
        when(status) {
            is Resource.Loading -> {

            }
            is Resource.DataError -> {
                status.errorMsg?.let { showToast(it) }
                startActivity(Intent(activity, LoginActivity::class.java))
            }
            else -> {
                status.data?.let {
                    if (it.type == CollectViewModel.COLLECT) {
                        showToast(getString(R.string.collect_success))
                        articleList[it.positon].collect = true
                        homeAdapter.setList(articleList)
                        return@let
                    }
                    articleList[it.positon].collect = false
                    homeAdapter.setList(articleList)
                    showToast(getString(R.string.cancel_collect_success))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        EventBus.getDefault().unregister(this)
    }

}