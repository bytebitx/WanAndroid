package com.bbgo.module_project.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.RouterPath
import com.bbgo.common_base.databinding.LayoutLoadingBinding
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.module_project.bean.ProjectBean
import com.bbgo.module_project.databinding.FragmentProjectBinding
import com.bbgo.module_project.viewmodel.ProjectViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 *  author: wangyb
 *  date: 2021/5/20 3:04 下午
 *  description: todo
 */
@Route(path = RouterPath.Project.PAGE_PROJECT)
@AndroidEntryPoint
class ProjectFragment : BaseFragment<FragmentProjectBinding>() {

    private lateinit var loadingBinding: LayoutLoadingBinding

    private val projectViewModel: ProjectViewModel by activityViewModels()

    /**
     * datas
     */
    private val projectDatas = mutableListOf<ProjectBean>()

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: ProjectPagerAdapter by lazy {
        ProjectPagerAdapter(this)
    }

    override fun lazyLoad() {
        projectViewModel.getProjectTree()
    }

    override fun initView() {
        loadingBinding = LayoutLoadingBinding.bind(binding.root)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = projectDatas[position].name
        }.attach()
    }

    override fun observe() {
        observe(projectViewModel.projectTreeLiveData, ::handleWxChapter)
    }

    private fun handleWxChapter(status: Resource<List<ProjectBean>>) {
        when(status) {
            is Resource.Loading -> {
                loadingBinding.progressBar.visibility = View.VISIBLE
            }
            is Resource.Error -> {
                loadingBinding.progressBar.visibility = View.GONE
                showToast(status.exception.toString())
            }
            is Resource.Success -> {
                loadingBinding.progressBar.visibility = View.GONE
                projectDatas.addAll(status.data)
                viewPagerAdapter.setList(projectDatas)
                binding.viewPager.offscreenPageLimit = projectDatas.size
            }
        }
    }

    companion object {
        private const val TAG = "WeChatFragment"
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProjectBinding.inflate(inflater, container, false)
}