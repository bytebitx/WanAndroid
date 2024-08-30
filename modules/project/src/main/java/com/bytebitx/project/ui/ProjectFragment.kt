package com.bytebitx.project.ui

import android.view.View
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.bytebitx.base.base.BaseFragment
import com.bytebitx.base.constants.RouterPath
import com.bytebitx.base.databinding.LayoutLoadingBinding
import com.bytebitx.base.ext.Resource
import com.bytebitx.base.ext.observe
import com.bytebitx.base.ext.showToast
import com.bytebitx.project.bean.ProjectBean
import com.bytebitx.project.databinding.FragmentProjectBinding
import com.bytebitx.project.viewmodel.ProjectViewModel
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

    override fun initObserver() {
        observe(projectViewModel.projectTreeLiveData, ::handleWxChapter)
    }

    override fun initView() {
        loadingBinding = LayoutLoadingBinding.bind(binding.root)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = projectDatas[position].name
        }.attach()
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
}