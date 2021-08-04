package com.bbgo.module_home.ui

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import cn.bingoogolapple.bgabanner.BGABanner
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.event.MessageEvent
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.common_base.util.ImageLoader
import com.bbgo.common_base.widget.SpaceItemDecoration
import com.bbgo.common_service.banner.BannerService
import com.bbgo.common_service.banner.bean.Banner
import com.bbgo.common_service.collect.CollectService
import com.bbgo.module_home.MessageCoard
import com.bbgo.module_home.R
import com.bbgo.module_home.bean.ArticleDetail
import com.bbgo.module_home.compose.dimens.*
import com.bbgo.module_home.databinding.FragmentHomeBinding
import com.bbgo.module_home.databinding.ItemHomeBannerBinding
import com.bbgo.module_home.util.InjectorUtil
import com.bbgo.module_home.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 *  author: wangyb
 *  date: 2021/5/20 3:00 下午
 *  description: todo
 */
@Route(path = Constants.NAVIGATION_TO_HOME_FRG)
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
    @Autowired
    lateinit var collectService: CollectService
    @Autowired
    lateinit var bannerService: BannerService

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
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Text(text = "Hello World")
            }
        }
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        bannerBinding = ItemHomeBannerBinding.inflate(inflater)
//        return _binding?.root
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
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
                        collectService.unCollect(position, articleList[position].id)
                        return@setOnItemChildClickListener
                    }
                    collectService.collect(position, articleList[position].id)
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        handleCollect(event)
    }

    private fun handleBanner(status: Resource<List<Banner>>) {
        when(status) {
            is Resource.Loading -> {

            }
            is Resource.DataError -> {
            }
            else -> {
                status.data?.let {
                    lifecycleScope.launch {
                        bannerService.insertBanners(it)
                    }
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

    private fun handleCollect(event: MessageEvent) {
        when (event.type) {
            Constants.CollectType.UNKNOWN -> {
                ARouter.getInstance().build(Constants.NAVIGATION_TO_LOGIN).navigation()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        EventBus.getDefault().unregister(this)
    }

    @Composable
    fun ArticleList(articleList: MutableList<ArticleDetail>) {
        /*Column {
            articleList.forEach {
                ItemCard(articleDetail = it)
            }
        }*/
        LazyColumn {
            items(articleList) { articleDetail ->
                ItemCard(articleDetail = articleDetail)
            }
        }
    }

    @Composable
    fun ItemCard(articleDetail: ArticleDetail) {
        Text(text = "Hello World")
    }

    @Composable
    fun TestItemCard() {
        val dp2 = dimensionResource(id = R.dimen.dp_2)
        val dp5 = dimensionResource(id = R.dimen.dp_5)
        Surface(shape = MaterialTheme.shapes.medium,
            elevation = dimensionResource(id = R.dimen.dp_1),
        ) {
            Column() {
                Spacer(modifier = Modifier.padding(vertical = dp2))
                Row {
                    Row(horizontalArrangement = Arrangement.Start) {
                        Spacer(modifier = Modifier.padding(horizontal = dp5))
                        Text(
                            text = "置顶",
                            color = Color.Red,
                            fontSize = sp10,
                            modifier = Modifier
                                .border(dp05, Color.Red, shape = RectangleShape)
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = dp5))
                        Text(
                            text = "新",
                            color = Color.Red,
                            fontSize = sp10,
                            modifier = Modifier
                                .border(dp05, Color.Red, shape = RectangleShape)
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = dp5))
                        Text(
                            text = "玩安卓",
                            color = Color.Red,
                            fontSize = sp10,
                            modifier = Modifier
                                .border(dp05, Color.Red, shape = RectangleShape)
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = dp5))
                        Text(
                            text = "玩安卓",
                            color = colorResource(id = R.color.Grey600),
                            fontSize = sp10,
                            modifier = Modifier
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.End) {
                        Spacer(modifier = Modifier.padding(horizontal = dp20))
                        Text(
                            text = "玩安卓",
                            color = colorResource(id = R.color.Grey600),
                            fontSize = sp10,
                            modifier = Modifier
                                .padding(start = dp4, end = dp4, top = dp2, bottom = dp2)
                        )
                    }

                }

                Spacer(modifier = Modifier.padding(vertical = dp2))

                Row(horizontalArrangement = Arrangement.Start) {
                    Spacer(modifier = Modifier.padding(horizontal = dp5))
                    Text(
                        text = "Compose 有效地处理嵌套布局，使其成为设计复杂UI的好方法。这是对 Android Views 的改进，在 Android Views 中，出于性能原因，您需要避免嵌套布局。你好呀陌生人，这是一个标题，不是很长，因为我想不出其他什么比较好的标题了",
                        color = colorResource(id = R.color.item_title),
                        fontSize = sp10,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = dp5),
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = dp2))

                ConstraintLayout {
                    val (text, image) = createRefs()
                    Text(
                        text = "问答 / 官方",
                        color = colorResource(id = R.color.item_title),
                        fontSize = sp8,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.constrainAs(text) {
                            start.linkTo(parent.start, margin = dp8)
                        }
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_like_not),
                        contentDescription = null,
                        Modifier
                            .constrainAs(image) {
                                end.linkTo(parent.end, margin = dp8)
                            }
                            .size(8.dp)
                            .clickable {

                            }
                    )


                }
                
            }
        }
    }


    @Preview
    @Composable
    fun DefaultPreview() {
//        ArticleList(articleList = mutableListOf())
        TestItemCard()
    }

}