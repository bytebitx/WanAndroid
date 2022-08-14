package com.bbgo.module_sys.ui

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.databinding.LayoutLoadingBinding
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.viewBinding
import com.bbgo.common_base.widget.SpaceItemDecoration
import com.bbgo.module_sys.R
import com.bbgo.module_sys.bean.KnowledgeTree
import com.bbgo.module_sys.databinding.FragmentRefreshLayoutBinding
import com.bbgo.module_sys.viewmodel.SysViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by wangyb
 */
@AndroidEntryPoint
class KnowledgeTreeFragment : BaseFragment(R.layout.fragment_refresh_layout) {

    companion object {
        fun getInstance(): KnowledgeTreeFragment {
            return KnowledgeTreeFragment()
        }
    }

    private val binding by viewBinding(FragmentRefreshLayoutBinding::bind)

    private lateinit var loadingBinding: LayoutLoadingBinding

    @Inject
    lateinit var sysViewModel: SysViewModel

    /**
     * datas
     */
    private var knowledgeTreeList = mutableListOf<KnowledgeTree>()

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
    private val mAdapter: KnowledgeTreeAdapter by lazy {
        KnowledgeTreeAdapter()
    }

    override fun initView() {
        loadingBinding = LayoutLoadingBinding.bind(binding.root)
        binding.swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        binding.swipeRefreshLayout.setOnRefreshListener {
            sysViewModel.getKnowledgeTree()
        }
        binding.recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = mAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }
    }

    override fun lazyLoad() {
        sysViewModel.getKnowledgeTree()
    }

    override fun observe() {
        observe(sysViewModel.treeLiveData, ::handleInfo)
    }

    private fun handleInfo(status: Resource<List<KnowledgeTree>>) {
        when(status) {
            is Resource.Loading -> {
                loadingBinding.progressBar.visibility = View.VISIBLE
            }
            else -> {
                loadingBinding.progressBar.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
                status.data?.let {
                    knowledgeTreeList.clear()
                    knowledgeTreeList.addAll(it)
                    mAdapter.setList(knowledgeTreeList)
                }
            }
        }
    }
}