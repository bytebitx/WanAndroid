package com.bytebitx.sys.ui

import android.text.Html
import com.bytebitx.sys.R
import com.bytebitx.sys.bean.KnowledgeTree
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Description:
 * @Author: wangyuebin
 * @Date: 2021/8/13 2:47 下午
 */
class KnowledgeTreeAdapter : BaseQuickAdapter<KnowledgeTree, BaseViewHolder>(R.layout.item_knowledge_tree_list),
    LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: KnowledgeTree) {
        holder.setText(R.id.title_first, item.name)
        item.children.let {
            holder.setText(
                R.id.title_second,
                it.joinToString("    ", transform = { child ->
                    Html.fromHtml(child.name)
                })
            )
        }
    }
}