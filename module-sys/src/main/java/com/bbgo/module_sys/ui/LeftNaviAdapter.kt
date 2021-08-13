package com.bbgo.module_sys.ui

import android.widget.TextView
import com.bbgo.module_sys.R
import com.bbgo.module_sys.bean.NaviData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *   Created by wangyb on 2019/4/28 17:44
 */
class LeftNaviAdapter(listData: ArrayList<NaviData>) :
    BaseQuickAdapter<NaviData, BaseViewHolder>(R.layout.item_navi, listData) {

    /**
     * kotlin中函数作为参数，不用再写接口
     */
    private lateinit var mOnItemSelectListener: (postion: Int) -> Unit

    fun setOnItemSelectListener(listener: (postion: Int) -> Unit)  {
        mOnItemSelectListener = listener
    }

    override fun convert(holder: BaseViewHolder, item: NaviData) {
        holder.setText(R.id.tv_title, item.name)
        holder.getView<TextView>(R.id.tv_title).isSelected = item.isSelected
    }

}