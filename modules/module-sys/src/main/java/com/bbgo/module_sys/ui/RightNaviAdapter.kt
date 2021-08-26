package com.bbgo.module_sys.ui

import androidx.recyclerview.widget.RecyclerView
import com.bbgo.common_base.BaseApplication
import com.bbgo.module_sys.R
import com.bbgo.module_sys.bean.NaviData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.*

/**
 *   Created by wangyb on 2019/4/28 17:44
 */
class RightNaviAdapter(listData: ArrayList<NaviData>) :
    BaseQuickAdapter<NaviData, BaseViewHolder>(R.layout.item_navi_content, listData) {

    override fun convert(holder: BaseViewHolder, item: NaviData) {
        holder.setText(R.id.navi_content_name, item.name)

        if (!item.articles.isNullOrEmpty()) {
            val flexBoxLayoutManager = FlexboxLayoutManager(BaseApplication.getContext())
            flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行
            flexBoxLayoutManager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
            flexBoxLayoutManager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
            flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式

            val mRecyclerView = holder.getView<RecyclerView>(R.id.navi_content_tag_rv)
            mRecyclerView.layoutManager = flexBoxLayoutManager
            mRecyclerView.adapter = NaviTagAdapter(item.articles)
        }
    }
}