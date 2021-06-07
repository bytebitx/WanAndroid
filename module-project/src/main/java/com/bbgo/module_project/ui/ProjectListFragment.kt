package com.bbgo.module_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.logD
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.widget.SpaceItemDecoration
import com.bbgo.module_project.bean.ArticleDetail
import com.bbgo.module_project.databinding.FragmentProjectListBinding
import com.bbgo.module_project.util.InjectorUtil
import com.bbgo.module_project.viewmodel.ProjectViewModel

/**
 * Created by wangyb
 */
class ProjectListFragment : BaseFragment() {

    companion object {
        fun getInstance(cid: Int): ProjectListFragment {
            val fragment = ProjectListFragment()
            val args = Bundle()
            args.putInt(Constants.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentProjectListBinding? = null
    private val binding get() = _binding!!
    private lateinit var projectViewModel: ProjectViewModel

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
        _binding = FragmentProjectListBinding.inflate(inflater, container, false)
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

        projectViewModel = ViewModelProvider(this, InjectorUtil.getProjectViewModelFactory())
            .get(ProjectViewModel::class.java)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val article = articleList[position]
            ARouter.getInstance().build(Constants.NAVIGATION_TO_CONTENT)
                .withString(Constants.POSITION, position.toString())
                .withString(Constants.CONTENT_ID_KEY, article.id.toString())
                .withString(Constants.CONTENT_TITLE_KEY, article.title)
                .withString(Constants.CONTENT_URL_KEY, article.link)
                .navigation()
        }
    }

    override fun lazyLoad() {
        projectViewModel.getProjectList(cid, 1)
    }

    override fun observeViewModel() {
        observe(projectViewModel.articlesLiveData, ::handleInfo)
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