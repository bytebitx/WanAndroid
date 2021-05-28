package com.bbgo.wanandroid.square.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.wanandroid.bean.ArticleDetail
import com.bbgo.wanandroid.databinding.FragmentHomeBinding
import com.bbgo.wanandroid.square.viewmodel.SquareViewModel
import com.bbgo.wanandroid.util.InjectorUtil
import com.bbgo.wanandroid.widget.SpaceItemDecoration

/**
 *  author: wangyb
 *  date: 2021/5/20 3:00 下午
 *  description: todo
 */
class SquareFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val squareViewModel: SquareViewModel by activityViewModels{
        InjectorUtil.getSquareViewModelFactory()
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
    private val homeAdapter: SquareAdapter by lazy {
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun initView() {
        binding.swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        binding.recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }
    }

    override fun lazyLoad() {
        squareViewModel.getSquareList(0)
    }

    override fun observeViewModel() {
        observe(squareViewModel.articleLiveData, ::handleInfo)
    }

    private fun handleInfo(articles: Resource<List<ArticleDetail>>) {
        when(articles) {
            is Resource.Loading -> {

            }
            else -> {
                articles.data?.let {
                    articleList.addAll(it)
                    if (isRefresh) {
                        homeAdapter.setList(it)
                    } else {
                        homeAdapter.addData(it)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}