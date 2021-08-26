package com.bbgo.module_project.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bbgo.module_project.bean.ProjectBean


class ProjectPagerAdapter(fm: Fragment)
    : FragmentStateAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun setList(list: List<ProjectBean>) {
        fragments.clear()
        list.forEach {
            fragments.add(ProjectListFragment.getInstance(it.id))
        }
        notifyDataSetChanged()
    }


}