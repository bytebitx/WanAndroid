package com.bbgo.module_project.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.bbgo.common_base.base.BaseFragment
import com.bbgo.common_base.constants.Constants
import com.bbgo.common_base.ext.Resource
import com.bbgo.common_base.ext.observe
import com.bbgo.common_base.ext.showToast
import com.bbgo.module_project.bean.ProjectBean
import com.bbgo.module_project.databinding.FragmentProjectBinding
import com.bbgo.module_project.util.InjectorUtil
import com.bbgo.module_project.viewmodel.ProjectViewModel
import com.google.android.material.tabs.TabLayoutMediator

/**
 *  author: wangyb
 *  date: 2021/5/20 3:04 下午
 *  description: todo
 */
@Route(path = Constants.NAVIGATION_TO_PROJECT_FRG)
class ProjectFragment : BaseFragment() {

    private var _binding: FragmentProjectBinding? = null
    private val binding get() = _binding!!
    private val projectViewModel: ProjectViewModel by activityViewModels{
        InjectorUtil.getProjectViewModelFactory()
    }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun lazyLoad() {
        projectViewModel.getProjectTree()
    }

    override fun initView() {
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true, true) { tab, position ->
            tab.text = projectDatas[position].name
        }.attach()
    }

    override fun observeViewModel() {
        observe(projectViewModel.projectTreeLiveData, ::handleWxChapter)
    }

    private fun handleWxChapter(status: Resource<List<ProjectBean>>) {
        when(status) {
            is Resource.DataError -> {
                showToast(status.errorMsg!!)
            }
            else -> {
                status.data?.let {
                    projectDatas.addAll(it)
                    viewPagerAdapter.setList(projectDatas)
                    binding.viewPager.offscreenPageLimit = projectDatas.size
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "WeChatFragment"
    }
}