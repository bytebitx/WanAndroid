package com.bbgo.module_wechat.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bbgo.module_wechat.bean.WXArticle

/**
 * @author chenxz
 * @date 2018/10/28
 * @desc
 */
class WeChatPagerAdapter(fm: Fragment)
    : FragmentStateAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun setList(list: List<WXArticle>) {
        fragments.clear()
        list.forEach {
            fragments.add(ArticleListFragment.getInstance(it.id))
        }
        notifyDataSetChanged()
    }


}