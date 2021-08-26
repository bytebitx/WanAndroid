package com.bbgo.module_sys.ui

import android.graphics.Color
import android.widget.TextView
import com.bbgo.module_sys.R
import com.bbgo.module_sys.bean.ArticleDetail
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*

/**
 *   Created by wangyb on 2019/4/28 17:44
 */
class NaviTagAdapter(listData: ArrayList<ArticleDetail>) :
    BaseQuickAdapter<ArticleDetail, BaseViewHolder>(R.layout.layout_navi_tag, listData) {


    override fun convert(holder: BaseViewHolder, item: ArticleDetail) {
        val mNaviTagView = holder.getView<TextView>(R.id.tv_navi_tag)
        mNaviTagView.text = item.title
        mNaviTagView.setTextColor(randomColor())
    }

    private fun randomColor(): Int {
        val random = Random()
        val red = random.nextInt(150) + 50//50-199
        val green = random.nextInt(150) + 50//50-199
        val blue = random.nextInt(150) + 50//50-199
        return Color.rgb(red, green, blue)
    }


}