package com.bbgo.module_sys.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.module_sys.bean.NaviData
import com.bbgo.module_sys.databinding.FragmentNavigationBinding
import com.bbgo.module_sys.viewmodel.SysViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by wangyb
 */
@AndroidEntryPoint
class NavigationFragment : BaseFragment() {

    companion object {
        fun getInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }

    private var _binding: FragmentNavigationBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sysViewModel: SysViewModel

    private var currentPosition = 0

    private var naviList = ArrayList<NaviData>()

    private val naviLayoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }
    private val naviContentLayoutManager by lazy {
        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }
    private val mAdapter by lazy {
        LeftNaviAdapter(naviList)
    }

    private val contentAdapter by lazy {
        RightNaviAdapter(naviList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        return _binding?.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun initView() {
        binding.refreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            contentAdapter.notifyDataSetChanged()
        }

        binding.rvNavi.layoutManager = naviLayoutManager
        binding.rvNavi.adapter = mAdapter

        binding.rvNaviDetail.layoutManager = naviContentLayoutManager
        binding.rvNaviDetail.adapter = contentAdapter


        mAdapter.setOnItemClickListener { _, _, position ->
            selectItem(position)
            moveToCenter(position)
            naviContentLayoutManager.scrollToPositionWithOffset(position, 0)
        }

        binding.rvNaviDetail.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemPosition = naviContentLayoutManager.findFirstVisibleItemPosition()
                if (currentPosition != itemPosition) {
                    selectItem(itemPosition)
                    moveToCenter(itemPosition)
                }
            }
        })
    }

    override fun lazyLoad() {
        sysViewModel.getNavigationList()
    }

    override fun observeViewModel() {
        observe(sysViewModel.naviLiveData, ::handleInfo)
    }

    private fun handleInfo(status: Resource<List<NaviData>>) {
        when(status) {
            is Resource.Loading -> {

            }
            else -> {
                status.data?.let {
                    naviList.clear()
                    naviList.addAll(it)
                    naviList[0].isSelected = true // 默认第一个被选中
                    contentAdapter.setList(naviList)
                    mAdapter.setList(naviList)
                }
            }
        }
    }

    private fun selectItem(position: Int) {
        if (position < 0 || naviList.size < position) {
            return
        }
        naviList[currentPosition].isSelected = false
        mAdapter.notifyItemChanged(currentPosition)
        currentPosition = position
        naviList[position].isSelected = true
        mAdapter.notifyItemChanged(position)
    }

    private fun moveToCenter(position: Int) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        val viewAt = binding.rvNavi.getChildAt(position - naviLayoutManager.findFirstVisibleItemPosition())
        if (viewAt != null) {
            val y = viewAt.top - binding.rvNavi.height / 2
            binding.rvNavi.smoothScrollBy(0, y)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}