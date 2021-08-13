package com.bbgo.module_wechat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.widget.SpaceItemDecoration
import com.bbgo.module_wechat.util.InjectorUtil
import com.bbgo.module_wechat.viewmodel.WeChatViewModel
import com.bbgo.module_wechat.bean.ArticleDetail
import com.bbgo.module_wechat.databinding.FragmentHomeBinding

/**
 * Created by wangyb
 */
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

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        isRefresh = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    override fun initView() {
        cid = arguments?.getInt(Constants.CONTENT_CID_KEY) ?: 0
        binding.swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
        binding.recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        weChatViewModel = ViewModelProvider(this, InjectorUtil.getWeChatViewModelFactory())
            .get(WeChatViewModel::class.java)
    }

    override fun lazyLoad() {
        weChatViewModel.getWXArticles(cid, 0)
    }

    override fun observeViewModel() {
        observe(weChatViewModel.wxArticlesLiveData, ::handleInfo)
    }

    private fun handleInfo(status: Resource<MutableList<ArticleDetail>>) {
        when(status) {
            is Resource.Loading -> {

            }
            else -> {
                status.data?.let {
                    articleList.clear()
                    articleList.addAll(it)
                    logD(mAdapter)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}