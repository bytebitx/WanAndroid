package com.bytebitx.sys.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class SysPagerAdapter(fm: Fragment) : FragmentStateAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

    init {
        fragments.add(KnowledgeTreeFragment.getInstance())
        fragments.add(NavigationFragment.getInstance())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}